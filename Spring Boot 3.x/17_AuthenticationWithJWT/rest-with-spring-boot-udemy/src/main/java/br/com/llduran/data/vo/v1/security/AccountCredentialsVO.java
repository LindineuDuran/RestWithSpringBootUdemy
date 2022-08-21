package br.com.llduran.data.vo.v1.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCredentialsVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
}
