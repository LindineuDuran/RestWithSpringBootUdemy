package br.com.llduran.data.vo.v2;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PersonVOV2 implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
	private Date birthDay;
}
