package br.com.llduran.security.jwt;

import br.com.llduran.data.vo.v1.security.TokenVO;
import br.com.llduran.exceptions.InvalidJwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider
{
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:3600000}")
	private final long validityInMilliseconds = 3600000; // 1h

	@Autowired
	private UserDetailsService userDetailsService;

	Algorithm algorithm;

	@PostConstruct
	protected void init()
	{
		this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
		this.algorithm = Algorithm.HMAC256(this.secretKey.getBytes());
	}

	public TokenVO createAccessToken(final String username, final List<String> roles)
	{
		final Date now = new Date();
		final Date validity = new Date(now.getTime() + this.validityInMilliseconds);
		final var accessToken = this.getAccessToken(username, roles, now, validity);
		final var refreshToken = this.getRefreshToken(username, roles, now);

		return new TokenVO(username, true, now, validity, accessToken, refreshToken);
	}

	public TokenVO refreshToken(String refreshToken)
	{
		if (refreshToken.contains("Bearer "))
			refreshToken = refreshToken.substring("Bearer ".length());

		final JWTVerifier verifier = JWT.require(this.algorithm).build();
		final DecodedJWT decodedJWT = verifier.verify(refreshToken);
		final String username = decodedJWT.getSubject();
		final List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		return this.createAccessToken(username, roles);
	}

	private String getAccessToken(final String username, final List<String> roles, final Date now, final Date validity)
	{
		final String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validity).withSubject(username)
				.withIssuer(issuerUrl).sign(this.algorithm).strip();
	}

	private String getRefreshToken(final String username, final List<String> roles, final Date now)
	{
		final Date validityRefreshToken = new Date(now.getTime() + (this.validityInMilliseconds * 3));
		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validityRefreshToken)
				.withSubject(username).sign(this.algorithm).strip();
	}

	public Authentication getAuthentication(final String token)
	{
		final DecodedJWT decodedJWT = this.decodedToken(token);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(final String token)
	{
		final Algorithm alg = Algorithm.HMAC256(this.secretKey.getBytes());
		final JWTVerifier verifier = JWT.require(alg).build();
		final DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
	}

	public String resolveToken(final HttpServletRequest req)
	{
		final String bearerToken = req.getHeader("Authorization");

		// Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFuZHJvIiwicm9sZXMiOlsiQURNSU4iLCJNQU5BR0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTY1MjcxOTUzOCwiaWF0IjoxNjUyNzE1OTM4fQ.muu8eStsRobqLyrFYLHRiEvOSHAcss4ohSNtmwWTRcY
		if (bearerToken != null && bearerToken.startsWith("Bearer "))
		{
			return bearerToken.substring("Bearer ".length());
		}
		return null;
	}

	public boolean validateToken(final String token)
	{
		final DecodedJWT decodedJWT = this.decodedToken(token);
		try
		{
			return !decodedJWT.getExpiresAt().before(new Date());
		}
		catch (final Exception e)
		{
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
		}
	}
}