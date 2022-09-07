package br.com.llduran.integrationtests.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.io.Serializable;

@Data
//@XmlRootElement
public class PersonVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	private String address;

	private String gender;

	private Boolean	enabled;
}
