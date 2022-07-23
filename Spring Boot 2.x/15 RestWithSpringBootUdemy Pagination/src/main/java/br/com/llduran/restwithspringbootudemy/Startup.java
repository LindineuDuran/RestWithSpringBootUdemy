package br.com.llduran.restwithspringbootudemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
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
