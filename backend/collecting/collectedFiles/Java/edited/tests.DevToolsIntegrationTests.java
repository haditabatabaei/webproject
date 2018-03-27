

package org.springframework.boot.devtools.tests;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FixedValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Parameterized.class)
public class DevToolsIntegrationTests {

	private LaunchedApplication launchedApplication;

	private final File serverPortFile = new File("target/server.port");

	private final ApplicationLauncher applicationLauncher;

	@Rule
	public JvmLauncher javaLauncher = new JvmLauncher();

	@Parameters(name = "{0}")
	public static Object[] parameters() {
		return new Object[] { new Object[] { new LocalApplicationLauncher() },
				new Object[] { new ExplodedRemoteApplicationLauncher() },
				new Object[] { new JarFileRemoteApplicationLauncher() } };
	}

	public DevToolsIntegrationTests(ApplicationLauncher applicationLauncher) {
		this.applicationLauncher = applicationLauncher;
	}

	@Before
	public void launchApplication() throws Exception {
		this.serverPortFile.delete();
		System.out.println("Launching " + this.javaLauncher.getClass());
		this.launchedApplication = this.applicationLauncher
				.launchApplication(this.javaLauncher);
	}

	@After
	public void stopApplication() throws InterruptedException {
		this.launchedApplication.stop();
	}

	@Test
	public void addARequestMappingToAnExistingController() throws Exception {
		TestRestTemplate template = new TestRestTemplate();
		String urlBase = "http:		assertThat(template.getForObject(urlBase + "/one", String.class))
				.isEqualTo("one");
		assertThat(template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerOne").withRequestMapping("one")
				.withRequestMapping("two").build();
		assertThat(template.getForObject(urlBase + "/one", String.class))
				.isEqualTo("one");
		assertThat(template.getForObject("http:				String.class)).isEqualTo("two");
	}

	@Test
	public void removeARequestMappingFromAnExistingController() throws Exception {
		TestRestTemplate template = new TestRestTemplate();
		assertThat(template.getForObject("http:				String.class)).isEqualTo("one");
		controller("com.example.ControllerOne").build();
		assertThat(template.getForEntity("http:				String.class).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void createAController() throws Exception {
		TestRestTemplate template = new TestRestTemplate();
		String urlBase = "http:		assertThat(template.getForObject(urlBase + "/one", String.class))
				.isEqualTo("one");
		assertThat(template.getForEntity(urlBase + "/two", String.class).getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
		controller("com.example.ControllerTwo").withRequestMapping("two").build();
		assertThat(template.getForObject(urlBase + "/one", String.class))
				.isEqualTo("one");
		assertThat(template.getForObject("http:				String.class)).isEqualTo("two");

	}

	@Test
	public void deleteAController() throws Exception {
		TestRestTemplate template = new TestRestTemplate();
		assertThat(template.getForObject("http:				String.class)).isEqualTo("one");
		assertThat(new File(this.launchedApplication.getClassesDirectory(),
				"com/example/ControllerOne.class").delete()).isTrue();
		assertThat(template.getForEntity("http:				String.class).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	private int awaitServerPort() throws Exception {
		long end = System.currentTimeMillis() + 30000;
		while (this.serverPortFile.length() == 0) {
			System.out.println("Getting server port " + this.serverPortFile.length());
			if (System.currentTimeMillis() > end) {
				throw new IllegalStateException(String.format(
						"server.port file was not written within 30 seconds. "
								+ "Application output:%n%s%s",
						FileCopyUtils.copyToString(new FileReader(
								this.launchedApplication.getStandardOut())),
						FileCopyUtils.copyToString(new FileReader(
								this.launchedApplication.getStandardError()))));
			}
			Thread.sleep(100);
		}
		FileReader portReader = new FileReader(this.serverPortFile);
		int port = Integer.valueOf(FileCopyUtils.copyToString(portReader));
		this.serverPortFile.delete();
		System.out.println("Got port " + port);
		return port;
	}

	private ControllerBuilder controller(String name) {
		return new ControllerBuilder(name,
				this.launchedApplication.getClassesDirectory());
	}

	private static final class ControllerBuilder {

		private final List<String> mappings = new ArrayList<>();

		private final String name;

		private final File classesDirectory;

		private ControllerBuilder(String name, File classesDirectory) {
			this.name = name;
			this.classesDirectory = classesDirectory;
		}

		public ControllerBuilder withRequestMapping(String mapping) {
			this.mappings.add(mapping);
			return this;
		}

		public void build() throws Exception {
			Builder<Object> builder = new ByteBuddy().subclass(Object.class)
					.name(this.name).annotateType(AnnotationDescription.Builder
							.ofType(RestController.class).build());
			for (String mapping : this.mappings) {
				builder = builder.defineMethod(mapping, String.class, Visibility.PUBLIC)
						.intercept(FixedValue.value(mapping)).annotateMethod(
								AnnotationDescription.Builder.ofType(RequestMapping.class)
										.defineArray("value", mapping).build());
			}
			builder.make().saveIn(this.classesDirectory);
		}

	}

}