package br.com.llduran.restwithspringbootudemy.services;

import br.com.llduran.restwithspringbootudemy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService
{
	@Autowired
	private UserRepository repository;

	public UserServices(UserRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		var user = repository.findByUserName(username);

		if (user != null)
		{
			return user;
		}
		else
		{
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}
}