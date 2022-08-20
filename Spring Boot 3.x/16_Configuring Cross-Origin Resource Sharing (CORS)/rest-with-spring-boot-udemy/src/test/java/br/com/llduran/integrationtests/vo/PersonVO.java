package br.com.llduran.integrationtests.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
}
