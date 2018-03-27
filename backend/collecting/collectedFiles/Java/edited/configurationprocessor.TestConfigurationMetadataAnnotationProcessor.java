

package org.springframework.boot.configurationprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.JsonMarshaller;


@SupportedAnnotationTypes({ "*" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class TestConfigurationMetadataAnnotationProcessor
		extends ConfigurationMetadataAnnotationProcessor {

	static final String CONFIGURATION_PROPERTIES_ANNOTATION = "org.springframework.boot.configurationsample.ConfigurationProperties";

	static final String NESTED_CONFIGURATION_PROPERTY_ANNOTATION = "org.springframework.boot.configurationsample.NestedConfigurationProperty";

	static final String DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION = "org.springframework.boot.configurationsample.DeprecatedConfigurationProperty";

	static final String ENDPOINT_ANNOTATION = "org.springframework.boot.configurationsample.Endpoint";

	static final String READ_OPERATION_ANNOTATION = "org.springframework.boot.configurationsample.ReadOperation";

	private ConfigurationMetadata metadata;

	private final File outputLocation;

	public TestConfigurationMetadataAnnotationProcessor(File outputLocation) {
		this.outputLocation = outputLocation;
	}

	@Override
	protected String configurationPropertiesAnnotation() {
		return CONFIGURATION_PROPERTIES_ANNOTATION;
	}

	@Override
	protected String nestedConfigurationPropertyAnnotation() {
		return NESTED_CONFIGURATION_PROPERTY_ANNOTATION;
	}

	@Override
	protected String deprecatedConfigurationPropertyAnnotation() {
		return DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION;
	}

	@Override
	protected String endpointAnnotation() {
		return ENDPOINT_ANNOTATION;
	}

	@Override
	protected String readOperationAnnotation() {
		return READ_OPERATION_ANNOTATION;
	}

	@Override
	protected ConfigurationMetadata writeMetaData() throws Exception {
		super.writeMetaData();
		try {
			File metadataFile = new File(this.outputLocation,
					"META-INF/spring-configuration-metadata.json");
			if (metadataFile.isFile()) {
				this.metadata = new JsonMarshaller()
						.read(new FileInputStream(metadataFile));
			}
			else {
				this.metadata = new ConfigurationMetadata();
			}
			return this.metadata;
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to read metadata from disk", e);
		}
	}

	public ConfigurationMetadata getMetadata() {
		return this.metadata;
	}

}
