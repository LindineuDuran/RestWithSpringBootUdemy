package br.com.llduran.integrationtests.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@XmlRootElement
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

	public TokenVO(
			final String username,
			final Boolean authenticated,
			final Date created,
			final Date expiration,
			final String accessToken,
			final String refreshToken)
	{
		this.username = username;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
