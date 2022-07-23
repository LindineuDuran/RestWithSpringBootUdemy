package br.com.llduran.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import br.com.llduran.repository.UserRepository;
import br.com.llduran.security.AccountCredentialsVO;
import br.com.llduran.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController
{
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UserRepository repository;

	@ApiOperation(value = "Authenticates a user and returns a token")
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signin", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data)
	{
		try
		{
			var username = data.getUsername();
			var pasword = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));

			var user = repository.findByUsername(username);

			var token = "";

			if (user != null)
			{
				token = tokenProvider.createToken(username, user.getRoles());
			}
			else
			{
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}

			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			return ok(model);
		}
		catch (AuthenticationException e)
		{
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
