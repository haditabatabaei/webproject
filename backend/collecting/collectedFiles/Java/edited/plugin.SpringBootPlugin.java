

package org.springframework.boot.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.util.GradleVersion;

import org.springframework.boot.gradle.dsl.SpringBootExtension;
import org.springframework.boot.gradle.tasks.bundling.BootJar;
import org.springframework.boot.gradle.tasks.bundling.BootWar;


public class SpringBootPlugin implements Plugin<Project> {

	private static final String SPRING_BOOT_VERSION = determineSpringBootVersion();

	
	public static final String BOOT_ARCHIVES_CONFIGURATION_NAME = "bootArchives";

	
	public static final String BOOT_JAR_TASK_NAME = "bootJar";

	
	public static final String BOOT_WAR_TASK_NAME = "bootWar";

	
	public static final String BOM_COORDINATES = "org.springframework.boot:spring-boot-dependencies:"
			+ SPRING_BOOT_VERSION;

	@Override
	public void apply(Project project) {
		verifyGradleVersion();
		createExtension(project);
		Configuration bootArchives = createBootArchivesConfiguration(project);
		registerPluginActions(project, bootArchives);
		unregisterUnresolvedDependenciesAnalyzer(project);
	}

	private void verifyGradleVersion() {
		if (GradleVersion.current().compareTo(GradleVersion.version("4.0")) < 0) {
			throw new GradleException("Spring Boot plugin requires Gradle 4.0 or later."
					+ " The current version is " + GradleVersion.current());
		}
	}

	private void createExtension(Project project) {
		project.getExtensions().create("springBoot", SpringBootExtension.class, project);
	}

	private Configuration createBootArchivesConfiguration(Project project) {
		Configuration bootArchives = project.getConfigurations()
				.create(BOOT_ARCHIVES_CONFIGURATION_NAME);
		bootArchives.setDescription("Configuration for Spring Boot archive artifacts.");
		return bootArchives;
	}

	private void registerPluginActions(Project project, Configuration bootArchives) {
		SinglePublishedArtifact singlePublishedArtifact = new SinglePublishedArtifact(
				bootArchives.getArtifacts());
		List<PluginApplicationAction> actions = Arrays.asList(
				new JavaPluginAction(singlePublishedArtifact),
				new WarPluginAction(singlePublishedArtifact),
				new MavenPluginAction(bootArchives.getUploadTaskName()),
				new DependencyManagementPluginAction(), new ApplicationPluginAction(),
				new KotlinPluginAction());
		for (PluginApplicationAction action : actions) {
			Class<? extends Plugin<? extends Project>> pluginClass = action
					.getPluginClass();
			if (pluginClass != null) {
				project.getPlugins().withType(pluginClass,
						(plugin) -> action.execute(project));
			}
		}
	}

	private void unregisterUnresolvedDependenciesAnalyzer(Project project) {
		UnresolvedDependenciesAnalyzer unresolvedDependenciesAnalyzer = new UnresolvedDependenciesAnalyzer();
		project.getConfigurations().all((configuration) -> configuration.getIncoming()
				.afterResolve((resolvableDependencies) -> unresolvedDependenciesAnalyzer
						.analyze(configuration.getResolvedConfiguration()
								.getLenientConfiguration()
								.getUnresolvedModuleDependencies())));
		project.getGradle().buildFinished(
				(buildResult) -> unresolvedDependenciesAnalyzer.buildFinished(project));
	}

	private static String determineSpringBootVersion() {
		String implementationVersion = DependencyManagementPluginAction.class.getPackage()
				.getImplementationVersion();
		if (implementationVersion != null) {
			return implementationVersion;
		}
		URL codeSourceLocation = DependencyManagementPluginAction.class
				.getProtectionDomain().getCodeSource().getLocation();
		try {
			URLConnection connection = codeSourceLocation.openConnection();
			if (connection instanceof JarURLConnection) {
				return getImplementationVersion(
						((JarURLConnection) connection).getJarFile());
			}
			try (JarFile jarFile = new JarFile(new File(codeSourceLocation.toURI()))) {
				return getImplementationVersion(jarFile);
			}
		}
		catch (Exception ex) {
			return null;
		}
	}

	private static String getImplementationVersion(JarFile jarFile) throws IOException {
		return jarFile.getManifest().getMainAttributes()
				.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
	}

}
