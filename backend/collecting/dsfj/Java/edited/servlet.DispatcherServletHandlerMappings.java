

package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.core.StandardWrapper;

import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;


final class DispatcherServletHandlerMappings {

	private final String name;

	private final DispatcherServlet dispatcherServlet;

	private final WebApplicationContext applicationContext;

	DispatcherServletHandlerMappings(String name, DispatcherServlet dispatcherServlet,
			WebApplicationContext applicationContext) {
		this.name = name;
		this.dispatcherServlet = dispatcherServlet;
		this.applicationContext = applicationContext;
	}

	public List<HandlerMapping> getHandlerMappings() {
		List<HandlerMapping> handlerMappings = this.dispatcherServlet
				.getHandlerMappings();
		if (handlerMappings == null) {
			initializeDispatcherServletIfPossible();
			handlerMappings = this.dispatcherServlet.getHandlerMappings();
		}
		return handlerMappings == null ? Collections.emptyList() : handlerMappings;
	}

	private void initializeDispatcherServletIfPossible() {
		if (!(this.applicationContext instanceof ServletWebServerApplicationContext)) {
			return;
		}
		WebServer webServer = ((ServletWebServerApplicationContext) this.applicationContext)
				.getWebServer();
		if (webServer instanceof UndertowServletWebServer) {
			new UndertowServletInitializer((UndertowServletWebServer) webServer)
					.initializeServlet(this.name);
		}
		else if (webServer instanceof TomcatWebServer) {
			new TomcatServletInitializer((TomcatWebServer) webServer)
					.initializeServlet(this.name);
		}
	}

	public String getName() {
		return this.name;
	}

	private static final class TomcatServletInitializer {

		private final TomcatWebServer webServer;

		private TomcatServletInitializer(TomcatWebServer webServer) {
			this.webServer = webServer;
		}

		void initializeServlet(String name) {
			findContext().ifPresent((context) -> initializeServlet(context, name));
		}

		private Optional<Context> findContext() {
			return Stream.of(this.webServer.getTomcat().getHost().findChildren())
					.filter(Context.class::isInstance).map(Context.class::cast)
					.findFirst();
		}

		private void initializeServlet(Context context, String name) {
			Container child = context.findChild(name);
			if (child instanceof StandardWrapper) {
				try {
					((StandardWrapper) child).allocate();
				}
				catch (ServletException ex) {
									}
			}
		}

	}

	private static final class UndertowServletInitializer {

		private final UndertowServletWebServer webServer;

		private UndertowServletInitializer(UndertowServletWebServer webServer) {
			this.webServer = webServer;
		}

		void initializeServlet(String name) {
			try {
				this.webServer.getDeploymentManager().getDeployment().getServlets()
						.getManagedServlet(name).forceInit();
			}
			catch (ServletException ex) {
							}
		}

	}

}