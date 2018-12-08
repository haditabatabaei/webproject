

package org.springframework.boot.test.mock.web;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletContext;


public class SpringBootMockServletContext extends MockServletContext {

	private static final String[] SPRING_BOOT_RESOURCE_LOCATIONS = new String[] {
			"classpath:META-INF/resources", "classpath:resources", "classpath:static",
			"classpath:public" };

	private final ResourceLoader resourceLoader;

	private File emptyRootFolder;

	public SpringBootMockServletContext(String resourceBasePath) {
		this(resourceBasePath, new FileSystemResourceLoader());
	}

	public SpringBootMockServletContext(String resourceBasePath,
			ResourceLoader resourceLoader) {
		super(resourceBasePath, resourceLoader);
		this.resourceLoader = resourceLoader;
	}

	@Override
	protected String getResourceLocation(String path) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		String resourceLocation = getResourceBasePathLocation(path);
		if (exists(resourceLocation)) {
			return resourceLocation;
		}
		for (String prefix : SPRING_BOOT_RESOURCE_LOCATIONS) {
			resourceLocation = prefix + path;
			if (exists(resourceLocation)) {
				return resourceLocation;
			}
		}
		return super.getResourceLocation(path);
	}

	protected final String getResourceBasePathLocation(String path) {
		return super.getResourceLocation(path);
	}

	private boolean exists(String resourceLocation) {
		try {
			Resource resource = this.resourceLoader.getResource(resourceLocation);
			return resource.exists();
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		URL resource = super.getResource(path);
		if (resource == null && "/".equals(path)) {
									try {
				if (this.emptyRootFolder == null) {
					synchronized (this) {
						File tempFolder = File.createTempFile("spr", "servlet");
						tempFolder.delete();
						tempFolder.mkdirs();
						tempFolder.deleteOnExit();
						this.emptyRootFolder = tempFolder;
					}
				}
				return this.emptyRootFolder.toURI().toURL();
			}
			catch (IOException ex) {
							}
		}
		return resource;
	}

}