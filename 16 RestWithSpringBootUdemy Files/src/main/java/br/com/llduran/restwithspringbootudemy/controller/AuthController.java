package br.com.llduran.restwithspringbootudemy.controller;

import br.com.llduran.restwithspringbootudemy.repository.UserRepository;
import br.com.llduran.restwithspringbootudemy.security.AccountCredentialVO;
import br.com.llduran.restwithspringbootudemy.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@Api(value = "", tags = { "Authentication" })
//@Tag(name = "Authentication", description = "Authenticates a user and returns a token")
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

	@ApiOperation(value = "Authenticate a user by credentials")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Authenticate a user by credentials") })
	@PostMapping(value = "/signin", consumes = { "application/json", "application/xml",
			"application/x-yaml" }, produces = { "application/json", "application/xml", "application/x-yaml" })
	@ResponseStatus(HttpStatus.CREATED)
	public Map<Object, Object> signin(@RequestBody AccountCredentialVO data)
	{
		try
		{
			var username = data.getUsername();
			var password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = repository.findByUserName(username);
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

			return model;
		}
		catch (AuthenticationException e)
		{
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
