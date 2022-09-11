package br.com.llduran.services;

import br.com.llduran.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService
{
	private final Logger logger = Logger.getLogger(UserServices.class.getName());

	@Autowired
	UserRepository repository;

	public UserServices(final UserRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		this.logger.info("Finding one user by name " + username + "!");
		final var user = this.repository.findByUsername(username);
		if (user != null)
		{
			return user;
		}
		else
		{
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
	}
}