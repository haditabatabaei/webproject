

package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;


class StaticResourceJars {

	List<URL> getUrls() {
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader instanceof URLClassLoader) {
			return getUrlsFrom(((URLClassLoader) classLoader).getURLs());
		}
		else {
			return getUrlsFrom(Stream
					.of(ManagementFactory.getRuntimeMXBean().getClassPath()
							.split(File.pathSeparator))
					.map(this::toUrl).toArray(URL[]::new));
		}
	}

	List<URL> getUrlsFrom(URL... urls) {
		List<URL> resourceJarUrls = new ArrayList<>();
		for (URL url : urls) {
			addUrl(resourceJarUrls, url);
		}
		return resourceJarUrls;
	}

	private URL toUrl(String classPathEntry) {
		try {
			return new File(classPathEntry).toURI().toURL();
		}
		catch (MalformedURLException ex) {
			throw new IllegalArgumentException(
					"URL could not be created from '" + classPathEntry + "'", ex);
		}
	}

	private void addUrl(List<URL> urls, URL url) {
		try {
			if ("file".equals(url.getProtocol())) {
				addUrlFile(urls, url, new File(getDecodedFile(url)));
			}
			else {
				addUrlConnection(urls, url, url.openConnection());
			}
		}
		catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	private String getDecodedFile(URL url) {
		try {
			return URLDecoder.decode(url.getFile(), "UTF-8");
		}
		catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException(
					"Failed to decode '" + url.getFile() + "' using UTF-8");
		}
	}

	private void addUrlFile(List<URL> urls, URL url, File file) {
		if ((file.isDirectory() && new File(file, "META-INF/resources").isDirectory())
				|| isResourcesJar(file)) {
			urls.add(url);
		}
	}

	private void addUrlConnection(List<URL> urls, URL url, URLConnection connection) {
		if (connection instanceof JarURLConnection
				&& isResourcesJar((JarURLConnection) connection)) {
			urls.add(url);
		}
	}

	private boolean isResourcesJar(JarURLConnection connection) {
		try {
			return isResourcesJar(connection.getJarFile());
		}
		catch (IOException ex) {
			return false;
		}
	}

	private boolean isResourcesJar(File file) {
		try {
			return isResourcesJar(new JarFile(file));
		}
		catch (IOException ex) {
			return false;
		}
	}

	private boolean isResourcesJar(JarFile jar) throws IOException {
		try {
			return jar.getName().endsWith(".jar")
					&& (jar.getJarEntry("META-INF/resources") != null);
		}
		finally {
			jar.close();
		}
	}

}
