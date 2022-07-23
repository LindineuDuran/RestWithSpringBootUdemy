package br.com.llduran.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Person implements Serializable
{
	private static final long serialVersionUID = 1L;

	private long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
}
