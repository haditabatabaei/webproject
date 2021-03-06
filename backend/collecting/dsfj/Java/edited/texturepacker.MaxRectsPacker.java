

package com.badlogic.gdx.tools.texturepacker;

import java.util.Comparator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Packer;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Page;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.ProgressListener;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Rect;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;


public class MaxRectsPacker implements Packer {
	private RectComparator rectComparator = new RectComparator();
	private FreeRectChoiceHeuristic[] methods = FreeRectChoiceHeuristic.values();
	private MaxRects maxRects = new MaxRects();
	Settings settings;
	private Sort sort = new Sort();

	public MaxRectsPacker (Settings settings) {
		this.settings = settings;
		if (settings.minWidth > settings.maxWidth) throw new RuntimeException("Page min width cannot be higher than max width.");
		if (settings.minHeight > settings.maxHeight)
			throw new RuntimeException("Page min height cannot be higher than max height.");
	}

	public Array<Page> pack (Array<Rect> inputRects) {
		return pack(null, inputRects);
	}

	public Array<Page> pack (ProgressListener progress, Array<Rect> inputRects) {
		int n = inputRects.size;
		for (int i = 0; i < n; i++) {
			Rect rect = inputRects.get(i);
			rect.width += settings.paddingX;
			rect.height += settings.paddingY;
		}

		if (settings.fast) {
			if (settings.rotation) {
								sort.sort(inputRects, new Comparator<Rect>() {
					public int compare (Rect o1, Rect o2) {
						int n1 = o1.width > o1.height ? o1.width : o1.height;
						int n2 = o2.width > o2.height ? o2.width : o2.height;
						return n2 - n1;
					}
				});
			} else {
								sort.sort(inputRects, new Comparator<Rect>() {
					public int compare (Rect o1, Rect o2) {
						return o2.width - o1.width;
					}
				});
			}
		}

		Array<Page> pages = new Array();
		while (inputRects.size > 0) {
			if (progress != null && progress.update(n - inputRects.size + 1, n)) break;
			Page result = packPage(progress, inputRects);
			pages.add(result);
			inputRects = result.remainingRects;
		}
		return pages;

	}

	private Page packPage (ProgressListener progress, Array<Rect> inputRects) {
		int paddingX = settings.paddingX, paddingY = settings.paddingY;
		float maxWidth = settings.maxWidth, maxHeight = settings.maxHeight;
		int edgePaddingX = 0, edgePaddingY = 0;
		if (settings.edgePadding) {
			if (settings.duplicatePadding) { 				maxWidth -= paddingX;
				maxHeight -= paddingY;
			} else {
				maxWidth -= paddingX * 2;
				maxHeight -= paddingY * 2;
				edgePaddingX = paddingX;
				edgePaddingY = paddingY;
			}
		}

				int minWidth = Integer.MAX_VALUE, minHeight = Integer.MAX_VALUE;
		for (int i = 0, nn = inputRects.size; i < nn; i++) {
			Rect rect = inputRects.get(i);
			minWidth = Math.min(minWidth, rect.width);
			minHeight = Math.min(minHeight, rect.height);
			float width = rect.width - paddingX, height = rect.height - paddingY;
			if (settings.rotation) {
				if ((width > maxWidth || height > maxHeight) && (width > maxHeight || height > maxWidth)) {
					String paddingMessage = (edgePaddingX > 0 || edgePaddingY > 0) ? (" and edge padding " + paddingX + "," + paddingY)
						: "";
					throw new RuntimeException("Image does not fit with max page size " + settings.maxWidth + "x" + settings.maxHeight
						+ paddingMessage + ": " + rect.name + "[" + width + "," + height + "]");
				}
			} else {
				if (width > maxWidth) {
					String paddingMessage = edgePaddingX > 0 ? (" and X edge padding " + paddingX) : "";
					throw new RuntimeException("Image does not fit with max page width " + settings.maxWidth + paddingMessage + ": "
						+ rect.name + "[" + width + "," + height + "]");
				}
				if (height > maxHeight && (!settings.rotation || width > maxHeight)) {
					String paddingMessage = edgePaddingY > 0 ? (" and Y edge padding " + paddingY) : "";
					throw new RuntimeException("Image does not fit in max page height " + settings.maxHeight + paddingMessage + ": "
						+ rect.name + "[" + width + "," + height + "]");
				}
			}
		}
		minWidth = Math.max(minWidth, settings.minWidth);
		minHeight = Math.max(minHeight, settings.minHeight);

		if (!settings.silent) System.out.print("Packing");

				Page bestResult = null;
		if (settings.square) {
			int minSize = Math.max(minWidth, minHeight);
			int maxSize = Math.min(settings.maxWidth, settings.maxHeight);
			BinarySearch sizeSearch = new BinarySearch(minSize, maxSize, settings.fast ? 25 : 15, settings.pot);
			int size = sizeSearch.reset(), i = 0;
			while (size != -1) {
				Page result = packAtSize(true, size - edgePaddingX, size - edgePaddingY, inputRects);
				if (!settings.silent) {
					if (++i % 70 == 0) System.out.println();
					System.out.print(".");
				}
				bestResult = getBest(bestResult, result);
				size = sizeSearch.next(result == null);
			}
			if (!settings.silent) System.out.println();
						if (bestResult == null) bestResult = packAtSize(false, maxSize - edgePaddingX, maxSize - edgePaddingY, inputRects);
			sort.sort(bestResult.outputRects, rectComparator);
			bestResult.width = Math.max(bestResult.width, bestResult.height);
			bestResult.height = Math.max(bestResult.width, bestResult.height);
			return bestResult;
		} else {
			BinarySearch widthSearch = new BinarySearch(minWidth, settings.maxWidth, settings.fast ? 25 : 15, settings.pot);
			BinarySearch heightSearch = new BinarySearch(minHeight, settings.maxHeight, settings.fast ? 25 : 15, settings.pot);
			int width = widthSearch.reset(), i = 0;
			int height = settings.square ? width : heightSearch.reset();
			while (true) {
				Page bestWidthResult = null;
				while (width != -1) {
					Page result = packAtSize(true, width - edgePaddingX, height - edgePaddingY, inputRects);
					if (!settings.silent) {
						if (++i % 70 == 0) System.out.println();
						System.out.print(".");
					}
					bestWidthResult = getBest(bestWidthResult, result);
					width = widthSearch.next(result == null);
					if (settings.square) height = width;
				}
				bestResult = getBest(bestResult, bestWidthResult);
				if (settings.square) break;
				height = heightSearch.next(bestWidthResult == null);
				if (height == -1) break;
				width = widthSearch.reset();
			}
			if (!settings.silent) System.out.println();
						if (bestResult == null)
				bestResult = packAtSize(false, settings.maxWidth - edgePaddingX, settings.maxHeight - edgePaddingY, inputRects);
			sort.sort(bestResult.outputRects, rectComparator);
			return bestResult;
		}
	}

	
	private Page packAtSize (boolean fully, int width, int height, Array<Rect> inputRects) {
		Page bestResult = null;
		for (int i = 0, n = methods.length; i < n; i++) {
			maxRects.init(width, height);
			Page result;
			if (!settings.fast) {
				result = maxRects.pack(inputRects, methods[i]);
			} else {
				Array<Rect> remaining = new Array();
				for (int ii = 0, nn = inputRects.size; ii < nn; ii++) {
					Rect rect = inputRects.get(ii);
					if (maxRects.insert(rect, methods[i]) == null) {
						while (ii < nn)
							remaining.add(inputRects.get(ii++));
					}
				}
				result = maxRects.getResult();
				result.remainingRects = remaining;
			}
			if (fully && result.remainingRects.size > 0) continue;
			if (result.outputRects.size == 0) continue;
			bestResult = getBest(bestResult, result);
		}
		return bestResult;
	}

	private Page getBest (Page result1, Page result2) {
		if (result1 == null) return result2;
		if (result2 == null) return result1;
		return result1.occupancy > result2.occupancy ? result1 : result2;
	}

	static class BinarySearch {
		int min, max, fuzziness, low, high, current;
		boolean pot;

		public BinarySearch (int min, int max, int fuzziness, boolean pot) {
			this.pot = pot;
			this.fuzziness = pot ? 0 : fuzziness;
			this.min = pot ? (int)(Math.log(MathUtils.nextPowerOfTwo(min)) / Math.log(2)) : min;
			this.max = pot ? (int)(Math.log(MathUtils.nextPowerOfTwo(max)) / Math.log(2)) : max;
		}

		public int reset () {
			low = min;
			high = max;
			current = (low + high) >>> 1;
			return pot ? (int)Math.pow(2, current) : current;
		}

		public int next (boolean result) {
			if (low >= high) return -1;
			if (result)
				low = current + 1;
			else
				high = current - 1;
			current = (low + high) >>> 1;
			if (Math.abs(low - high) < fuzziness) return -1;
			return pot ? (int)Math.pow(2, current) : current;
		}
	}

	
	class MaxRects {
		private int binWidth;
		private int binHeight;
		private final Array<Rect> usedRectangles = new Array();
		private final Array<Rect> freeRectangles = new Array();

		public void init (int width, int height) {
			binWidth = width;
			binHeight = height;

			usedRectangles.clear();
			freeRectangles.clear();
			Rect n = new Rect();
			n.x = 0;
			n.y = 0;
			n.width = width;
			n.height = height;
			freeRectangles.add(n);
		}

		
		public Rect insert (Rect rect, FreeRectChoiceHeuristic method) {
			Rect newNode = scoreRect(rect, method);
			if (newNode.height == 0) return null;

			int numRectanglesToProcess = freeRectangles.size;
			for (int i = 0; i < numRectanglesToProcess; ++i) {
				if (splitFreeNode(freeRectangles.get(i), newNode)) {
					freeRectangles.removeIndex(i);
					--i;
					--numRectanglesToProcess;
				}
			}

			pruneFreeList();

			Rect bestNode = new Rect();
			bestNode.set(rect);
			bestNode.score1 = newNode.score1;
			bestNode.score2 = newNode.score2;
			bestNode.x = newNode.x;
			bestNode.y = newNode.y;
			bestNode.width = newNode.width;
			bestNode.height = newNode.height;
			bestNode.rotated = newNode.rotated;

			usedRectangles.add(bestNode);
			return bestNode;
		}

		
		public Page pack (Array<Rect> rects, FreeRectChoiceHeuristic method) {
			rects = new Array(rects);
			while (rects.size > 0) {
				int bestRectIndex = -1;
				Rect bestNode = new Rect();
				bestNode.score1 = Integer.MAX_VALUE;
				bestNode.score2 = Integer.MAX_VALUE;

								for (int i = 0; i < rects.size; i++) {
					Rect newNode = scoreRect(rects.get(i), method);
					if (newNode.score1 < bestNode.score1 || (newNode.score1 == bestNode.score1 && newNode.score2 < bestNode.score2)) {
						bestNode.set(rects.get(i));
						bestNode.score1 = newNode.score1;
						bestNode.score2 = newNode.score2;
						bestNode.x = newNode.x;
						bestNode.y = newNode.y;
						bestNode.width = newNode.width;
						bestNode.height = newNode.height;
						bestNode.rotated = newNode.rotated;
						bestRectIndex = i;
					}
				}

				if (bestRectIndex == -1) break;

				placeRect(bestNode);
				rects.removeIndex(bestRectIndex);
			}

			Page result = getResult();
			result.remainingRects = rects;
			return result;
		}

		public Page getResult () {
			int w = 0, h = 0;
			for (int i = 0; i < usedRectangles.size; i++) {
				Rect rect = usedRectangles.get(i);
				w = Math.max(w, rect.x + rect.width);
				h = Math.max(h, rect.y + rect.height);
			}
			Page result = new Page();
			result.outputRects = new Array(usedRectangles);
			result.occupancy = getOccupancy();
			result.width = w;
			result.height = h;
			return result;
		}

		private void placeRect (Rect node) {
			int numRectanglesToProcess = freeRectangles.size;
			for (int i = 0; i < numRectanglesToProcess; i++) {
				if (splitFreeNode(freeRectangles.get(i), node)) {
					freeRectangles.removeIndex(i);
					--i;
					--numRectanglesToProcess;
				}
			}

			pruneFreeList();

			usedRectangles.add(node);
		}

		private Rect scoreRect (Rect rect, FreeRectChoiceHeuristic method) {
			int width = rect.width;
			int height = rect.height;
			int rotatedWidth = height - settings.paddingY + settings.paddingX;
			int rotatedHeight = width - settings.paddingX + settings.paddingY;
			boolean rotate = rect.canRotate && settings.rotation;

			Rect newNode = null;
			switch (method) {
			case BestShortSideFit:
				newNode = findPositionForNewNodeBestShortSideFit(width, height, rotatedWidth, rotatedHeight, rotate);
				break;
			case BottomLeftRule:
				newNode = findPositionForNewNodeBottomLeft(width, height, rotatedWidth, rotatedHeight, rotate);
				break;
			case ContactPointRule:
				newNode = findPositionForNewNodeContactPoint(width, height, rotatedWidth, rotatedHeight, rotate);
				newNode.score1 = -newNode.score1; 				break;
			case BestLongSideFit:
				newNode = findPositionForNewNodeBestLongSideFit(width, height, rotatedWidth, rotatedHeight, rotate);
				break;
			case BestAreaFit:
				newNode = findPositionForNewNodeBestAreaFit(width, height, rotatedWidth, rotatedHeight, rotate);
				break;
			}

						if (newNode.height == 0) {
				newNode.score1 = Integer.MAX_VALUE;
				newNode.score2 = Integer.MAX_VALUE;
			}

			return newNode;
		}

				private float getOccupancy () {
			int usedSurfaceArea = 0;
			for (int i = 0; i < usedRectangles.size; i++)
				usedSurfaceArea += usedRectangles.get(i).width * usedRectangles.get(i).height;
			return (float)usedSurfaceArea / (binWidth * binHeight);
		}

		private Rect findPositionForNewNodeBottomLeft (int width, int height, int rotatedWidth, int rotatedHeight, boolean rotate) {
			Rect bestNode = new Rect();

			bestNode.score1 = Integer.MAX_VALUE; 
			for (int i = 0; i < freeRectangles.size; i++) {
								if (freeRectangles.get(i).width >= width && freeRectangles.get(i).height >= height) {
					int topSideY = freeRectangles.get(i).y + height;
					if (topSideY < bestNode.score1 || (topSideY == bestNode.score1 && freeRectangles.get(i).x < bestNode.score2)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = width;
						bestNode.height = height;
						bestNode.score1 = topSideY;
						bestNode.score2 = freeRectangles.get(i).x;
						bestNode.rotated = false;
					}
				}
				if (rotate && freeRectangles.get(i).width >= rotatedWidth && freeRectangles.get(i).height >= rotatedHeight) {
					int topSideY = freeRectangles.get(i).y + rotatedHeight;
					if (topSideY < bestNode.score1 || (topSideY == bestNode.score1 && freeRectangles.get(i).x < bestNode.score2)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = rotatedWidth;
						bestNode.height = rotatedHeight;
						bestNode.score1 = topSideY;
						bestNode.score2 = freeRectangles.get(i).x;
						bestNode.rotated = true;
					}
				}
			}
			return bestNode;
		}

		private Rect findPositionForNewNodeBestShortSideFit (int width, int height, int rotatedWidth, int rotatedHeight,
			boolean rotate) {
			Rect bestNode = new Rect();
			bestNode.score1 = Integer.MAX_VALUE;

			for (int i = 0; i < freeRectangles.size; i++) {
								if (freeRectangles.get(i).width >= width && freeRectangles.get(i).height >= height) {
					int leftoverHoriz = Math.abs(freeRectangles.get(i).width - width);
					int leftoverVert = Math.abs(freeRectangles.get(i).height - height);
					int shortSideFit = Math.min(leftoverHoriz, leftoverVert);
					int longSideFit = Math.max(leftoverHoriz, leftoverVert);

					if (shortSideFit < bestNode.score1 || (shortSideFit == bestNode.score1 && longSideFit < bestNode.score2)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = width;
						bestNode.height = height;
						bestNode.score1 = shortSideFit;
						bestNode.score2 = longSideFit;
						bestNode.rotated = false;
					}
				}

				if (rotate && freeRectangles.get(i).width >= rotatedWidth && freeRectangles.get(i).height >= rotatedHeight) {
					int flippedLeftoverHoriz = Math.abs(freeRectangles.get(i).width - rotatedWidth);
					int flippedLeftoverVert = Math.abs(freeRectangles.get(i).height - rotatedHeight);
					int flippedShortSideFit = Math.min(flippedLeftoverHoriz, flippedLeftoverVert);
					int flippedLongSideFit = Math.max(flippedLeftoverHoriz, flippedLeftoverVert);

					if (flippedShortSideFit < bestNode.score1
						|| (flippedShortSideFit == bestNode.score1 && flippedLongSideFit < bestNode.score2)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = rotatedWidth;
						bestNode.height = rotatedHeight;
						bestNode.score1 = flippedShortSideFit;
						bestNode.score2 = flippedLongSideFit;
						bestNode.rotated = true;
					}
				}
			}

			return bestNode;
		}

		private Rect findPositionForNewNodeBestLongSideFit (int width, int height, int rotatedWidth, int rotatedHeight,
			boolean rotate) {
			Rect bestNode = new Rect();

			bestNode.score2 = Integer.MAX_VALUE;

			for (int i = 0; i < freeRectangles.size; i++) {
								if (freeRectangles.get(i).width >= width && freeRectangles.get(i).height >= height) {
					int leftoverHoriz = Math.abs(freeRectangles.get(i).width - width);
					int leftoverVert = Math.abs(freeRectangles.get(i).height - height);
					int shortSideFit = Math.min(leftoverHoriz, leftoverVert);
					int longSideFit = Math.max(leftoverHoriz, leftoverVert);

					if (longSideFit < bestNode.score2 || (longSideFit == bestNode.score2 && shortSideFit < bestNode.score1)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = width;
						bestNode.height = height;
						bestNode.score1 = shortSideFit;
						bestNode.score2 = longSideFit;
						bestNode.rotated = false;
					}
				}

				if (rotate && freeRectangles.get(i).width >= rotatedWidth && freeRectangles.get(i).height >= rotatedHeight) {
					int leftoverHoriz = Math.abs(freeRectangles.get(i).width - rotatedWidth);
					int leftoverVert = Math.abs(freeRectangles.get(i).height - rotatedHeight);
					int shortSideFit = Math.min(leftoverHoriz, leftoverVert);
					int longSideFit = Math.max(leftoverHoriz, leftoverVert);

					if (longSideFit < bestNode.score2 || (longSideFit == bestNode.score2 && shortSideFit < bestNode.score1)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = rotatedWidth;
						bestNode.height = rotatedHeight;
						bestNode.score1 = shortSideFit;
						bestNode.score2 = longSideFit;
						bestNode.rotated = true;
					}
				}
			}
			return bestNode;
		}

		private Rect findPositionForNewNodeBestAreaFit (int width, int height, int rotatedWidth, int rotatedHeight,
			boolean rotate) {
			Rect bestNode = new Rect();

			bestNode.score1 = Integer.MAX_VALUE; 
			for (int i = 0; i < freeRectangles.size; i++) {
				int areaFit = freeRectangles.get(i).width * freeRectangles.get(i).height - width * height;

								if (freeRectangles.get(i).width >= width && freeRectangles.get(i).height >= height) {
					int leftoverHoriz = Math.abs(freeRectangles.get(i).width - width);
					int leftoverVert = Math.abs(freeRectangles.get(i).height - height);
					int shortSideFit = Math.min(leftoverHoriz, leftoverVert);

					if (areaFit < bestNode.score1 || (areaFit == bestNode.score1 && shortSideFit < bestNode.score2)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = width;
						bestNode.height = height;
						bestNode.score2 = shortSideFit;
						bestNode.score1 = areaFit;
						bestNode.rotated = false;
					}
				}

				if (rotate && freeRectangles.get(i).width >= rotatedWidth && freeRectangles.get(i).height >= rotatedHeight) {
					int leftoverHoriz = Math.abs(freeRectangles.get(i).width - rotatedWidth);
					int leftoverVert = Math.abs(freeRectangles.get(i).height - rotatedHeight);
					int shortSideFit = Math.min(leftoverHoriz, leftoverVert);

					if (areaFit < bestNode.score1 || (areaFit == bestNode.score1 && shortSideFit < bestNode.score2)) {
						bestNode.x = freeRectangles.get(i).x;
						bestNode.y = freeRectangles.get(i).y;
						bestNode.width = rotatedWidth;
						bestNode.height = rotatedHeight;
						bestNode.score2 = shortSideFit;
						bestNode.score1 = areaFit;
						bestNode.rotated = true;
					}
				}
			}
			return bestNode;
		}

				private int commonIntervalLength (int i1start, int i1end, int i2start, int i2end) {
			if (i1end < i2start || i2end < i1start) return 0;
			return Math.min(i1end, i2end) - Math.max(i1start, i2start);
		}

		private int contactPointScoreNode (int x, int y, int width, int height) {
			int score = 0;

			if (x == 0 || x + width == binWidth) score += height;
			if (y == 0 || y + height == binHeight) score += width;

			Array<Rect> usedRectangles = this.usedRectangles;
			for (int i = 0, n = usedRectangles.size; i < n; i++) {
				Rect rect = usedRectangles.get(i);
				if (rect.x == x + width || rect.x + rect.width == x)
					score += commonIntervalLength(rect.y, rect.y + rect.height, y, y + height);
				if (rect.y == y + height || rect.y + rect.height == y)
					score += commonIntervalLength(rect.x, rect.x + rect.width, x, x + width);
			}
			return score;
		}

		private Rect findPositionForNewNodeContactPoint (int width, int height, int rotatedWidth, int rotatedHeight,
			boolean rotate) {

			Rect bestNode = new Rect();
			bestNode.score1 = -1; 
			Array<Rect> freeRectangles = this.freeRectangles;
			for (int i = 0, n = freeRectangles.size; i < n; i++) {
								Rect free = freeRectangles.get(i);
				if (free.width >= width && free.height >= height) {
					int score = contactPointScoreNode(free.x, free.y, width, height);
					if (score > bestNode.score1) {
						bestNode.x = free.x;
						bestNode.y = free.y;
						bestNode.width = width;
						bestNode.height = height;
						bestNode.score1 = score;
						bestNode.rotated = false;
					}
				}
				if (rotate && free.width >= rotatedWidth && free.height >= rotatedHeight) {
					int score = contactPointScoreNode(free.x, free.y, rotatedWidth, rotatedHeight);
					if (score > bestNode.score1) {
						bestNode.x = free.x;
						bestNode.y = free.y;
						bestNode.width = rotatedWidth;
						bestNode.height = rotatedHeight;
						bestNode.score1 = score;
						bestNode.rotated = true;
					}
				}
			}
			return bestNode;
		}

		private boolean splitFreeNode (Rect freeNode, Rect usedNode) {
						if (usedNode.x >= freeNode.x + freeNode.width || usedNode.x + usedNode.width <= freeNode.x
				|| usedNode.y >= freeNode.y + freeNode.height || usedNode.y + usedNode.height <= freeNode.y) return false;

			if (usedNode.x < freeNode.x + freeNode.width && usedNode.x + usedNode.width > freeNode.x) {
								if (usedNode.y > freeNode.y && usedNode.y < freeNode.y + freeNode.height) {
					Rect newNode = new Rect(freeNode);
					newNode.height = usedNode.y - newNode.y;
					freeRectangles.add(newNode);
				}

								if (usedNode.y + usedNode.height < freeNode.y + freeNode.height) {
					Rect newNode = new Rect(freeNode);
					newNode.y = usedNode.y + usedNode.height;
					newNode.height = freeNode.y + freeNode.height - (usedNode.y + usedNode.height);
					freeRectangles.add(newNode);
				}
			}

			if (usedNode.y < freeNode.y + freeNode.height && usedNode.y + usedNode.height > freeNode.y) {
								if (usedNode.x > freeNode.x && usedNode.x < freeNode.x + freeNode.width) {
					Rect newNode = new Rect(freeNode);
					newNode.width = usedNode.x - newNode.x;
					freeRectangles.add(newNode);
				}

								if (usedNode.x + usedNode.width < freeNode.x + freeNode.width) {
					Rect newNode = new Rect(freeNode);
					newNode.x = usedNode.x + usedNode.width;
					newNode.width = freeNode.x + freeNode.width - (usedNode.x + usedNode.width);
					freeRectangles.add(newNode);
				}
			}

			return true;
		}

		private void pruneFreeList () {
			

						Array<Rect> freeRectangles = this.freeRectangles;
			for (int i = 0, n = freeRectangles.size; i < n; i++)
				for (int j = i + 1; j < n; ++j) {
					Rect rect1 = freeRectangles.get(i);
					Rect rect2 = freeRectangles.get(j);
					if (isContainedIn(rect1, rect2)) {
						freeRectangles.removeIndex(i);
						--i;
						--n;
						break;
					}
					if (isContainedIn(rect2, rect1)) {
						freeRectangles.removeIndex(j);
						--j;
						--n;
					}
				}
		}

		private boolean isContainedIn (Rect a, Rect b) {
			return a.x >= b.x && a.y >= b.y && a.x + a.width <= b.x + b.width && a.y + a.height <= b.y + b.height;
		}
	}

	static public enum FreeRectChoiceHeuristic {
				BestShortSideFit,
				BestLongSideFit,
				BestAreaFit,
				BottomLeftRule,
				ContactPointRule
	};

	class RectComparator implements Comparator<Rect> {
		public int compare (Rect o1, Rect o2) {
			return Rect.getAtlasName(o1.name, settings.flattenPaths).compareTo(Rect.getAtlasName(o2.name, settings.flattenPaths));
		}
	}
}
