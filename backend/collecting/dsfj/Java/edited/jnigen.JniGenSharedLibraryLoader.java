

package com.badlogic.gdx.jnigen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class JniGenSharedLibraryLoader {
	private static Set<String> loadedLibraries = new HashSet<String>();
	private String nativesJar;
	private SharedLibraryFinder libraryFinder;

	private ZipFile nativesZip = null;

	public JniGenSharedLibraryLoader () {
	}

	
	public JniGenSharedLibraryLoader (String nativesJar) {
		this.nativesJar = nativesJar;
	}

	
	public JniGenSharedLibraryLoader (String nativesJar, SharedLibraryFinder libraryFinder) {
		this.nativesJar = nativesJar;
		this.libraryFinder = libraryFinder;
		if (nativesJar != null) {
			try {
				nativesZip = new ZipFile(nativesJar);
			} catch (IOException e) {
				nativesZip = null;
			}
		}
	}

	
	public void setSharedLibraryFinder (SharedLibraryFinder libraryFinder) {
		this.libraryFinder = libraryFinder;
		if (nativesJar != null) {
			try {
				nativesZip = new ZipFile(nativesJar);
			} catch (IOException e) {
				nativesZip = null;
			}
		}
	}

	
	public String crc (InputStream input) {
		if (input == null) return "" + System.nanoTime(); 		CRC32 crc = new CRC32();
		byte[] buffer = new byte[4096];
		try {
			while (true) {
				int length = input.read(buffer);
				if (length == -1) break;
				crc.update(buffer, 0, length);
			}
		} catch (Exception ex) {
			try {
				input.close();
			} catch (Exception ignored) {
			}
		}
		return Long.toString(crc.getValue());
	}

	private boolean loadLibrary (String sharedLibName) {
		if (sharedLibName == null) return false;

		String path = extractLibrary(sharedLibName);
		if (path != null) System.load(path);
		return path != null;
	}

	private String extractLibrary (String sharedLibName) {
		String srcCrc = crc(JniGenSharedLibraryLoader.class.getResourceAsStream("/" + sharedLibName));
		File nativesDir = new File(System.getProperty("java.io.tmpdir") + "/jnigen/" + srcCrc);
		File nativeFile = new File(nativesDir, sharedLibName);

		String extractedCrc = null;
		if (nativeFile.exists()) {
			try {
				extractedCrc = crc(new FileInputStream(nativeFile));
			} catch (FileNotFoundException ignored) {
			}
		}

		if (extractedCrc == null || !extractedCrc.equals(srcCrc)) {
			try {
								InputStream input = null;
				if (nativesJar == null)
					input = JniGenSharedLibraryLoader.class.getResourceAsStream("/" + sharedLibName);
				else
					input = getFromJar(nativesJar, sharedLibName);
				if (input == null) return null;
				nativeFile.getParentFile().mkdirs();
				FileOutputStream output = new FileOutputStream(nativeFile);
				byte[] buffer = new byte[4096];
				while (true) {
					int length = input.read(buffer);
					if (length == -1) break;
					output.write(buffer, 0, length);
				}
				input.close();
				output.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		return nativeFile.exists() ? nativeFile.getAbsolutePath() : null;
	}

	private InputStream getFromJar (String jarFile, String sharedLibrary) throws IOException {
		ZipFile file = new ZipFile(nativesJar);
		ZipEntry entry = file.getEntry(sharedLibrary);
		return file.getInputStream(entry);
	}

	
	public synchronized void load (String sharedLibName) {
		if (loadedLibraries.contains(sharedLibName)) return;

		boolean isWindows = System.getProperty("os.name").contains("Windows");
		boolean isLinux = System.getProperty("os.name").contains("Linux");
		boolean isMac = System.getProperty("os.name").contains("Mac");
		boolean isAndroid = false;
		boolean is64Bit = System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64");
		boolean isArm = System.getProperty("os.arch").equals("arm");

		String vm = System.getProperty("java.vm.name");
		if (vm != null && vm.contains("Dalvik")) {
			isAndroid = true;
			isWindows = false;
			isLinux = false;
			isMac = false;
			is64Bit = false;
		}

		boolean loaded = false;
		if (isWindows) {
			if (libraryFinder != null)
				loaded = loadLibrary(libraryFinder.getSharedLibraryNameWindows(sharedLibName, is64Bit, nativesZip));
			else if (!is64Bit)
				loaded = loadLibrary(sharedLibName + ".dll");
			else
				loaded = loadLibrary(sharedLibName + "64.dll");
		}
		if (isLinux) {
			if (libraryFinder != null)
				loaded = loadLibrary(libraryFinder.getSharedLibraryNameLinux(sharedLibName, is64Bit, isArm, nativesZip));
			else if (!is64Bit) {
				if (isArm)
					loaded = loadLibrary("lib" + sharedLibName + "Arm.so");
				else
					loaded = loadLibrary("lib" + sharedLibName + ".so");
			} else {
				if (isArm)
					loaded = loadLibrary("lib" + sharedLibName + "Arm64.so");
				else
					loaded = loadLibrary("lib" + sharedLibName + "64.so");
			}
		}
		if (isMac) {
			if (libraryFinder != null)
				loaded = loadLibrary(libraryFinder.getSharedLibraryNameMac(sharedLibName, is64Bit, nativesZip));
			else if (!is64Bit)
				loaded = loadLibrary("lib" + sharedLibName + ".dylib");
			else
				loaded = loadLibrary("lib" + sharedLibName + "64.dylib");
		}
		if (isAndroid) {
			if (libraryFinder != null)
				System.loadLibrary(libraryFinder.getSharedLibraryNameAndroid(sharedLibName, nativesZip));
			else
				System.loadLibrary(sharedLibName);
			loaded = true;
		}
		if (loaded) loadedLibraries.add(sharedLibName);
	}
}