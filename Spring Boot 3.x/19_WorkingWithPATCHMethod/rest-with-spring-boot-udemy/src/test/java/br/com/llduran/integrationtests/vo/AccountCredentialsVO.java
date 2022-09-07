package br.com.llduran.integrationtests.vo;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class AccountCredentialsVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public AccountCredentialsVO() {}

	public AccountCredentialsVO(final String username, final String password)
	{
		this.username = username;
		this.password = password;
	}
}
