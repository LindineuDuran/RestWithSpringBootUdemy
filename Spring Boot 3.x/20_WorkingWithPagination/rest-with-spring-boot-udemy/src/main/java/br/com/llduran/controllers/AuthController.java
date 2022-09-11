package br.com.llduran.controllers;

import br.com.llduran.data.vo.v1.security.AccountCredentialsVO;
import br.com.llduran.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints for Managing Access")
@RestController @RequestMapping("/auth")
public class AuthController
{
	@Autowired
	AuthServices authServices;

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a user and returns a token")
	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody final AccountCredentialsVO data)
	{
		if (this.checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		final var token = this.authServices.signin(data);
		if (token == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		return token;
	}

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping("/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") final String username,
			@RequestHeader("Authorization") final String refreshToken)
	{
		if (this.checkIfParamsIsNotNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		final var token = this.authServices.refreshToken(username, refreshToken);
		if (token == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		return token;
	}

	private boolean checkIfParamsIsNotNull(final String username, final String refreshToken)
	{
		return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(final AccountCredentialsVO data)
	{
		return data == null || data.getUsername() == null || data.getUsername()
				.isBlank() || data.getPassword() == null || data.getPassword().isBlank();
	}
}