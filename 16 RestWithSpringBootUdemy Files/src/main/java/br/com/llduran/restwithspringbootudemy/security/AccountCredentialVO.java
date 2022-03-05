package br.com.llduran.restwithspringbootudemy.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCredentialVO implements Serializable
{
	private String username;
	private String password;
}
