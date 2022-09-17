package br.com.llduran.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
{
	@Autowired private final JwtTokenProvider tokenProvider;

	public JwtConfigurer(final JwtTokenProvider tokenProvider)
	{
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void configure(final HttpSecurity http) throws Exception
	{
		final JwtTokenFilter customFilter = new JwtTokenFilter(this.tokenProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}