

package org.springframework.boot.autoconfigure.jackson;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;


@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonAutoConfiguration {

	private static final Map<?, Boolean> FEATURE_DEFAULTS;

	static {
		Map<Object, Boolean> featureDefaults = new HashMap<>();
		featureDefaults.put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		FEATURE_DEFAULTS = Collections.unmodifiableMap(featureDefaults);
	}

	@Bean
	public JsonComponentModule jsonComponentModule() {
		return new JsonComponentModule();
	}

	@Configuration
	@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
	static class JacksonObjectMapperConfiguration {

		@Bean
		@Primary
		@ConditionalOnMissingBean(ObjectMapper.class)
		public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
			return builder.createXmlMapper(false).build();
		}

	}

	@Configuration
	@ConditionalOnClass({ Jackson2ObjectMapperBuilder.class, DateTime.class,
			DateTimeSerializer.class, JacksonJodaDateFormat.class })
	static class JodaDateTimeJacksonConfiguration {

		private static final Log logger = LogFactory
				.getLog(JodaDateTimeJacksonConfiguration.class);

		private final JacksonProperties jacksonProperties;

		JodaDateTimeJacksonConfiguration(JacksonProperties jacksonProperties) {
			this.jacksonProperties = jacksonProperties;
		}

		@Bean
		public SimpleModule jodaDateTimeSerializationModule() {
			SimpleModule module = new SimpleModule();
			JacksonJodaDateFormat jacksonJodaFormat = getJacksonJodaDateFormat();
			if (jacksonJodaFormat != null) {
				module.addSerializer(DateTime.class,
						new DateTimeSerializer(jacksonJodaFormat, 0));
			}
			return module;
		}

		private JacksonJodaDateFormat getJacksonJodaDateFormat() {
			if (this.jacksonProperties.getJodaDateTimeFormat() != null) {
				return new JacksonJodaDateFormat(DateTimeFormat
						.forPattern(this.jacksonProperties.getJodaDateTimeFormat())
						.withZoneUTC());
			}
			if (this.jacksonProperties.getDateFormat() != null) {
				try {
					return new JacksonJodaDateFormat(DateTimeFormat
							.forPattern(this.jacksonProperties.getDateFormat())
							.withZoneUTC());
				}
				catch (IllegalArgumentException ex) {
					if (logger.isWarnEnabled()) {
						logger.warn("spring.jackson.date-format could not be used to "
								+ "configure formatting of Joda's DateTime. You may want "
								+ "to configure spring.jackson.joda-date-time-format as "
								+ "well.");
					}
				}
			}
			return null;
		}

	}

	@Configuration
	@ConditionalOnClass(ParameterNamesModule.class)
	static class ParameterNamesModuleConfiguration {

		@Bean
		@ConditionalOnMissingBean(ParameterNamesModule.class)
		public ParameterNamesModule parameterNamesModule() {
			return new ParameterNamesModule(JsonCreator.Mode.DEFAULT);
		}

	}

	@Configuration
	@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
	static class JacksonObjectMapperBuilderConfiguration {

		private final ApplicationContext applicationContext;

		JacksonObjectMapperBuilderConfiguration(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}

		@Bean
		@ConditionalOnMissingBean(Jackson2ObjectMapperBuilder.class)
		public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder(
				List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
			builder.applicationContext(this.applicationContext);
			customize(builder, customizers);
			return builder;
		}

		private void customize(Jackson2ObjectMapperBuilder builder,
				List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
			for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
				customizer.customize(builder);
			}
		}

	}

	@Configuration
	@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
	@EnableConfigurationProperties(JacksonProperties.class)
	static class Jackson2ObjectMapperBuilderCustomizerConfiguration {

		@Bean
		public StandardJackson2ObjectMapperBuilderCustomizer standardJacksonObjectMapperBuilderCustomizer(
				ApplicationContext applicationContext,
				JacksonProperties jacksonProperties) {
			return new StandardJackson2ObjectMapperBuilderCustomizer(applicationContext,
					jacksonProperties);
		}

		private static final class StandardJackson2ObjectMapperBuilderCustomizer
				implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

			private final ApplicationContext applicationContext;

			private final JacksonProperties jacksonProperties;

			StandardJackson2ObjectMapperBuilderCustomizer(
					ApplicationContext applicationContext,
					JacksonProperties jacksonProperties) {
				this.applicationContext = applicationContext;
				this.jacksonProperties = jacksonProperties;
			}

			@Override
			public int getOrder() {
				return 0;
			}

			@Override
			public void customize(Jackson2ObjectMapperBuilder builder) {

				if (this.jacksonProperties.getDefaultPropertyInclusion() != null) {
					builder.serializationInclusion(
							this.jacksonProperties.getDefaultPropertyInclusion());
				}
				if (this.jacksonProperties.getTimeZone() != null) {
					builder.timeZone(this.jacksonProperties.getTimeZone());
				}
				configureFeatures(builder, FEATURE_DEFAULTS);
				configureFeatures(builder, this.jacksonProperties.getDeserialization());
				configureFeatures(builder, this.jacksonProperties.getSerialization());
				configureFeatures(builder, this.jacksonProperties.getMapper());
				configureFeatures(builder, this.jacksonProperties.getParser());
				configureFeatures(builder, this.jacksonProperties.getGenerator());
				configureDateFormat(builder);
				configurePropertyNamingStrategy(builder);
				configureModules(builder);
				configureLocale(builder);
			}

			private void configureFeatures(Jackson2ObjectMapperBuilder builder,
					Map<?, Boolean> features) {
				features.forEach((feature, value) -> {
					if (value != null) {
						if (value) {
							builder.featuresToEnable(feature);
						}
						else {
							builder.featuresToDisable(feature);
						}
					}
				});
			}

			private void configureDateFormat(Jackson2ObjectMapperBuilder builder) {
												String dateFormat = this.jacksonProperties.getDateFormat();
				if (dateFormat != null) {
					try {
						Class<?> dateFormatClass = ClassUtils.forName(dateFormat, null);
						builder.dateFormat(
								(DateFormat) BeanUtils.instantiateClass(dateFormatClass));
					}
					catch (ClassNotFoundException ex) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								dateFormat);
																								TimeZone timeZone = this.jacksonProperties.getTimeZone();
						if (timeZone == null) {
							timeZone = new ObjectMapper().getSerializationConfig()
									.getTimeZone();
						}
						simpleDateFormat.setTimeZone(timeZone);
						builder.dateFormat(simpleDateFormat);
					}
				}
			}

			private void configurePropertyNamingStrategy(
					Jackson2ObjectMapperBuilder builder) {
																				String strategy = this.jacksonProperties.getPropertyNamingStrategy();
				if (strategy != null) {
					try {
						configurePropertyNamingStrategyClass(builder,
								ClassUtils.forName(strategy, null));
					}
					catch (ClassNotFoundException ex) {
						configurePropertyNamingStrategyField(builder, strategy);
					}
				}
			}

			private void configurePropertyNamingStrategyClass(
					Jackson2ObjectMapperBuilder builder,
					Class<?> propertyNamingStrategyClass) {
				builder.propertyNamingStrategy((PropertyNamingStrategy) BeanUtils
						.instantiateClass(propertyNamingStrategyClass));
			}

			private void configurePropertyNamingStrategyField(
					Jackson2ObjectMapperBuilder builder, String fieldName) {
												Field field = ReflectionUtils.findField(PropertyNamingStrategy.class,
						fieldName, PropertyNamingStrategy.class);
				Assert.notNull(field, "Constant named '" + fieldName + "' not found on "
						+ PropertyNamingStrategy.class.getName());
				try {
					builder.propertyNamingStrategy(
							(PropertyNamingStrategy) field.get(null));
				}
				catch (Exception ex) {
					throw new IllegalStateException(ex);
				}
			}

			private void configureModules(Jackson2ObjectMapperBuilder builder) {
				Collection<Module> moduleBeans = getBeans(this.applicationContext,
						Module.class);
				builder.modulesToInstall(moduleBeans.toArray(new Module[0]));
			}

			private void configureLocale(Jackson2ObjectMapperBuilder builder) {
				Locale locale = this.jacksonProperties.getLocale();
				if (locale != null) {
					builder.locale(locale);
				}
			}

			private static <T> Collection<T> getBeans(ListableBeanFactory beanFactory,
					Class<T> type) {
				return BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, type)
						.values();
			}

		}

	}

}