package br.com.llduran.data.vo.v1.security;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TokenVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String username;
	private Boolean authenticated;
	private Date created;
	private Date expiration;
	private String accessToken;
	private String refreshToken;

	public TokenVO() {}

	public TokenVO(final String username, final boolean b, final Date now, final Date validity, final String accessToken, final String refreshToken)
	{
		this.username = username;
		authenticated = b;
		created = now;
		expiration = validity;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
