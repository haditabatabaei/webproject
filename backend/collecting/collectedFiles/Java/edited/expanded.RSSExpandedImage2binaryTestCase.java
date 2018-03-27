



package com.google.zxing.oned.rss.expanded;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.BufferedImageLuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.common.AbstractBlackBoxTestCase;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.GlobalHistogramBinarizer;
import org.junit.Assert;
import org.junit.Test;


public final class RSSExpandedImage2binaryTestCase extends Assert {

  @Test
  public void testDecodeRow2binary_1() throws Exception {
        assertCorrectImage2binary(
        "1.png", " ...X...X .X....X. .XX...X. X..X...X ...XX.X. ..X.X... ..X.X..X ...X..X. X.X....X .X....X. .....X.. X...X...");
  }

  @Test
  public void testDecodeRow2binary_2() throws Exception {
        assertCorrectImage2binary("2.png", " ..X..... ......X. .XXX.X.X .X...XX. XXXXX.XX XX.X.... .XX.XX.X .XX.");
  }

  @Test
  public void testDecodeRow2binary_3() throws Exception {
        assertCorrectImage2binary("3.png", " .......X ..XX..X. X.X....X .......X ....");
  }

  @Test
  public void testDecodeRow2binary_4() throws Exception {
        assertCorrectImage2binary(
        "4.png", " ..XXXX.X XX.XXXX. .XXX.XX. XX..X... .XXXXX.. XX.X..X. ..XX..XX XX.X.XXX X..XX..X .X.XXXXX XXXX");
  }

  @Test
  public void testDecodeRow2binary_5() throws Exception {
        assertCorrectImage2binary(
        "5.png", " ..X.X... .XXXX.X. XX..XXXX ....XX.. X....... ....X... ....X..X .XX.");
  }

  @Test
  public void testDecodeRow2binary_10() throws Exception {
        assertCorrectImage2binary(
        "10.png", " .X.XX..X XX.XXXX. .XXX.XX. XX..X... .XXXXX.. XX.X..X. ..XX...X XX.X.... X.X.X.X. X.X..X.X .X....X. XX...X.. ...XX.X. .XXXXXX. .X..XX.. X.X.X... .X...... XXXX.... XX.XX... XXXXX.X. ...XXXXX .....X.X ...X.... X.XXX..X X.X.X... XX.XX..X .X..X..X .X.X.X.X X.XX...X .XX.XXX. XXX.X.XX ..X.");
  }

  @Test
  public void testDecodeRow2binary_11() throws Exception {
        assertCorrectImage2binary(
        "11.png", " .X.XX..X XX.XXXX. .XXX.XX. XX..X... .XXXXX.. XX.X..X. ..XX...X XX.X.... X.X.X.X. X.X..X.X .X....X. XX...X.. ...XX.X. .XXXXXX. .X..XX.. X.X.X... .X...... XXXX.... XX.XX... XXXXX.X. ...XXXXX .....X.X ...X.... X.XXX..X X.X.X... ....");
  }

  @Test
  public void testDecodeRow2binary_12() throws Exception {
        assertCorrectImage2binary(
        "12.png", " ..X..XX. XXXX..XX X.XX.XX. .X....XX XXX..XX. X..X.... .XX.XX.X .XX.");
  }

  @Test
  public void testDecodeRow2binary_13() throws Exception {
        assertCorrectImage2binary(
        "13.png", " ..XX..X. ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. X.X.XXXX .X..X..X ......X.");
  }

  @Test
  public void testDecodeRow2binary_14() throws Exception {
        assertCorrectImage2binary(
        "14.png", " ..XX.X.. ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. X.....X. X.....X. X.X.X.XX .X...... X...");
  }

  @Test
  public void testDecodeRow2binary_15() throws Exception {
        assertCorrectImage2binary(
        "15.png", " ..XXX... ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_16() throws Exception {
        assertCorrectImage2binary(
        "16.png", " ..XXX..X ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_17() throws Exception {
        assertCorrectImage2binary(
        "17.png", " ..XXX.X. ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_18() throws Exception {
        assertCorrectImage2binary(
        "18.png", " ..XXX.XX ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_19() throws Exception {
        assertCorrectImage2binary(
        "19.png", " ..XXXX.. ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_20() throws Exception {
        assertCorrectImage2binary(
        "20.png", " ..XXXX.X ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_21() throws Exception {
        assertCorrectImage2binary(
        "21.png", " ..XXXXX. ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  @Test
  public void testDecodeRow2binary_22() throws Exception {
        assertCorrectImage2binary(
        "22.png", " ..XXXXXX ........ .X..XXX. X.X.X... XX.XXXXX .XXXX.X. ..XX...X .X.....X .XX..... XXXX.X.. XX..");
  }

  private static void assertCorrectImage2binary(String fileName, String expected)
      throws IOException, NotFoundException {
    Path path = AbstractBlackBoxTestCase.buildTestBase("src/test/resources/blackbox/rssexpanded-1/").resolve(fileName);

    BufferedImage image = ImageIO.read(path.toFile());
    BinaryBitmap binaryMap = new BinaryBitmap(new GlobalHistogramBinarizer(new BufferedImageLuminanceSource(image)));
    int rowNumber = binaryMap.getHeight() / 2;
    BitArray row = binaryMap.getBlackRow(rowNumber, null);

    List<ExpandedPair> pairs;
    try {
      RSSExpandedReader rssExpandedReader = new RSSExpandedReader();
      pairs = rssExpandedReader.decodeRow2pairs(rowNumber, row);
    } catch (ReaderException re) {
      fail(re.toString());
      return;
    }
    BitArray binary = BitArrayBuilder.buildBitArray(pairs);
    assertEquals(expected, binary.toString());
  }
}
