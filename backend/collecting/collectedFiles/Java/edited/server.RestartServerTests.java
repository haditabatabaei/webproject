

package org.springframework.boot.devtools.restart.server;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import org.springframework.boot.devtools.restart.classloader.ClassLoaderFile;
import org.springframework.boot.devtools.restart.classloader.ClassLoaderFile.Kind;
import org.springframework.boot.devtools.restart.classloader.ClassLoaderFiles;
import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;


public class RestartServerTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	@Test
	public void sourceFolderUrlFilterMustNotBeNull() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("SourceFolderUrlFilter must not be null");
		new RestartServer((SourceFolderUrlFilter) null);
	}

	@Test
	public void updateAndRestart() throws Exception {
		URL url1 = new URL("file:/proj/module-a.jar!/");
		URL url2 = new URL("file:/proj/module-b.jar!/");
		URL url3 = new URL("file:/proj/module-c.jar!/");
		URL url4 = new URL("file:/proj/module-d.jar!/");
		URLClassLoader classLoaderA = new URLClassLoader(new URL[] { url1, url2 });
		URLClassLoader classLoaderB = new URLClassLoader(new URL[] { url3, url4 },
				classLoaderA);
		SourceFolderUrlFilter filter = new DefaultSourceFolderUrlFilter();
		MockRestartServer server = new MockRestartServer(filter, classLoaderB);
		ClassLoaderFiles files = new ClassLoaderFiles();
		ClassLoaderFile fileA = new ClassLoaderFile(Kind.ADDED, new byte[0]);
		ClassLoaderFile fileB = new ClassLoaderFile(Kind.ADDED, new byte[0]);
		files.addFile("my/module-a", "ClassA.class", fileA);
		files.addFile("my/module-c", "ClassB.class", fileB);
		server.updateAndRestart(files);
		Set<URL> expectedUrls = new LinkedHashSet<>(Arrays.asList(url1, url3));
		assertThat(server.restartUrls).isEqualTo(expectedUrls);
		assertThat(server.restartFiles).isEqualTo(files);
	}

	@Test
	public void updateSetsJarLastModified() throws Exception {
		long startTime = System.currentTimeMillis();
		File folder = this.temp.newFolder();
		File jarFile = new File(folder, "module-a.jar");
		new FileOutputStream(jarFile).close();
		jarFile.setLastModified(0);
		URL url = jarFile.toURI().toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[] { url });
		SourceFolderUrlFilter filter = new DefaultSourceFolderUrlFilter();
		MockRestartServer server = new MockRestartServer(filter, classLoader);
		ClassLoaderFiles files = new ClassLoaderFiles();
		ClassLoaderFile fileA = new ClassLoaderFile(Kind.ADDED, new byte[0]);
		files.addFile("my/module-a", "ClassA.class", fileA);
		server.updateAndRestart(files);
		assertThat(jarFile.lastModified()).isGreaterThan(startTime - 1000);
	}

	@Test
	public void updateReplacesLocalFilesWhenPossible() throws Exception {
								File folder = this.temp.newFolder();
		File classFile = new File(folder, "ClassA.class");
		FileCopyUtils.copy("abc".getBytes(), classFile);
		URL url = folder.toURI().toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[] { url });
		SourceFolderUrlFilter filter = new DefaultSourceFolderUrlFilter();
		MockRestartServer server = new MockRestartServer(filter, classLoader);
		ClassLoaderFiles files = new ClassLoaderFiles();
		ClassLoaderFile fileA = new ClassLoaderFile(Kind.ADDED, "def".getBytes());
		files.addFile("my/module-a", "ClassA.class", fileA);
		server.updateAndRestart(files);
		assertThat(FileCopyUtils.copyToByteArray(classFile)).isEqualTo("def".getBytes());
	}

	private static class MockRestartServer extends RestartServer {

		MockRestartServer(SourceFolderUrlFilter sourceFolderUrlFilter,
				ClassLoader classLoader) {
			super(sourceFolderUrlFilter, classLoader);
		}

		private Set<URL> restartUrls;

		private ClassLoaderFiles restartFiles;

		@Override
		protected void restart(Set<URL> urls, ClassLoaderFiles files) {
			this.restartUrls = urls;
			this.restartFiles = files;
		}

	}

}
