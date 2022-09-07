package br.com.llduran.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest
{
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
	{
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.29");

		private static void startContainers()
		{
			Startables.deepStart(Stream.of(Initializer.mysql)).join();
		}

		private static Map<String, String> createConnectionConfiguration()
		{
			return Map.of("spring.datasource.url", Initializer.mysql.getJdbcUrl(), "spring.datasource.username", Initializer.mysql.getUsername(), "spring.datasource.password", Initializer.mysql.getPassword());
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void initialize(final ConfigurableApplicationContext applicationContext)
		{
			Initializer.startContainers();
			final ConfigurableEnvironment environment = applicationContext.getEnvironment();
			final MapPropertySource testcontainers = new MapPropertySource("testcontainers",
					(Map) Initializer.createConnectionConfiguration());
			environment.getPropertySources().addFirst(testcontainers);
		}
	}
}