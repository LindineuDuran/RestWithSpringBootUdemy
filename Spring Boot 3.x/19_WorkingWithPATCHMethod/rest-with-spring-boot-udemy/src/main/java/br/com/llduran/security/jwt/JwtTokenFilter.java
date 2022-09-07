package br.com.llduran.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean
{
	@Autowired
	private final JwtTokenProvider tokenProvider;

	public JwtTokenFilter(final JwtTokenProvider tokenProvider)
	{
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException
	{
		final String token = this.tokenProvider.resolveToken((HttpServletRequest) request);
		if (token != null && this.tokenProvider.validateToken(token))
		{
			final Authentication auth = this.tokenProvider.getAuthentication(token);
			if (auth != null)
			{
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}
}