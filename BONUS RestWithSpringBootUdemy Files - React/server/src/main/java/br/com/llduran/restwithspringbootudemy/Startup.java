package br.com.llduran.restwithspringbootudemy;

import br.com.llduran.restwithspringbootudemy.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableAutoConfiguration
@ComponentScan
public class Startup
{

	public static void main(String[] args)
	{
		SpringApplication.run(Startup.class, args);

		// Criar nova senha
		/*BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);

		String result = bCryptPasswordEncoder.encode("admin123");

		System.out.println("My hash " + result);*/
	}
}
