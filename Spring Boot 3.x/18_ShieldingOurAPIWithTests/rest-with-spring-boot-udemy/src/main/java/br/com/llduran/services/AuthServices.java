package br.com.llduran.services;

import br.com.llduran.data.vo.v1.security.AccountCredentialsVO;
import br.com.llduran.data.vo.v1.security.TokenVO;
import br.com.llduran.repositories.UserRepository;
import br.com.llduran.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices
{
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserRepository repository;

	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(final AccountCredentialsVO data)
	{
		try
		{
			final var username = data.getUsername();
			final var password = data.getPassword();
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			final var user = this.repository.findByUsername(username);

			var tokenResponse = new TokenVO();
			if (user != null)
			{
				tokenResponse = this.tokenProvider.createAccessToken(username, user.getRoles());
			}
			else
			{
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}

			return ResponseEntity.ok(tokenResponse);
		}
		catch (final Exception e)
		{
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(final String username, final String refreshToken)
	{
		final var user = this.repository.findByUsername(username);

		var tokenResponse = new TokenVO();
		if (user != null)
		{
			tokenResponse = this.tokenProvider.refreshToken(refreshToken);
		}
		else
		{
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}