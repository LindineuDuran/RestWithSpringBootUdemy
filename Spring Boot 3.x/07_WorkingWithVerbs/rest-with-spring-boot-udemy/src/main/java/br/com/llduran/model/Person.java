package br.com.llduran.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
}
