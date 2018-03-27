

package org.springframework.boot.maven;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import org.springframework.boot.loader.tools.JavaExecutable;
import org.springframework.boot.loader.tools.RunProcess;


@Mojo(name = "run", requiresProject = true, defaultPhase = LifecyclePhase.VALIDATE, requiresDependencyResolution = ResolutionScope.TEST)
@Execute(phase = LifecyclePhase.TEST_COMPILE)
public class RunMojo extends AbstractRunMojo {

	private static final int EXIT_CODE_SIGINT = 130;

	private static final String RESTARTER_CLASS_LOCATION = "org/springframework/boot/devtools/restart/Restarter.class";

	
	private Boolean hasDevtools;

	@Override
	protected boolean enableForkByDefault() {
		return super.enableForkByDefault() || hasDevtools();
	}

	@Override
	protected void logDisabledFork() {
		super.logDisabledFork();
		if (hasDevtools()) {
			getLog().warn("Fork mode disabled, devtools will be disabled");
		}
	}

	@Override
	protected void runWithForkedJvm(File workingDirectory, List<String> args)
			throws MojoExecutionException {
		try {
			RunProcess runProcess = new RunProcess(workingDirectory,
					new JavaExecutable().toString());
			Runtime.getRuntime()
					.addShutdownHook(new Thread(new RunProcessKiller(runProcess)));
			int exitCode = runProcess.run(true, args.toArray(new String[0]));
			if (exitCode == 0 || exitCode == EXIT_CODE_SIGINT) {
				return;
			}
			throw new MojoExecutionException(
					"Application finished with exit code: " + exitCode);
		}
		catch (Exception ex) {
			throw new MojoExecutionException("Could not exec java", ex);
		}
	}

	@Override
	protected void runWithMavenJvm(String startClassName, String... arguments)
			throws MojoExecutionException {
		IsolatedThreadGroup threadGroup = new IsolatedThreadGroup(startClassName);
		Thread launchThread = new Thread(threadGroup,
				new LaunchRunner(startClassName, arguments), "main");
		launchThread.setContextClassLoader(new URLClassLoader(getClassPathUrls()));
		launchThread.start();
		join(threadGroup);
		threadGroup.rethrowUncaughtException();
	}

	private void join(ThreadGroup threadGroup) {
		boolean hasNonDaemonThreads;
		do {
			hasNonDaemonThreads = false;
			Thread[] threads = new Thread[threadGroup.activeCount()];
			threadGroup.enumerate(threads);
			for (Thread thread : threads) {
				if (thread != null && !thread.isDaemon()) {
					try {
						hasNonDaemonThreads = true;
						thread.join();
					}
					catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
		while (hasNonDaemonThreads);
	}

	private boolean hasDevtools() {
		if (this.hasDevtools == null) {
			this.hasDevtools = checkForDevtools();
		}
		return this.hasDevtools;
	}

	private boolean checkForDevtools() {
		try {
			URL[] urls = getClassPathUrls();
			try (URLClassLoader classLoader = new URLClassLoader(urls)) {
				return (classLoader.findResource(RESTARTER_CLASS_LOCATION) != null);
			}
		}
		catch (Exception ex) {
			return false;
		}
	}

	private static final class RunProcessKiller implements Runnable {

		private final RunProcess runProcess;

		private RunProcessKiller(RunProcess runProcess) {
			this.runProcess = runProcess;
		}

		@Override
		public void run() {
			this.runProcess.kill();
		}

	}

}