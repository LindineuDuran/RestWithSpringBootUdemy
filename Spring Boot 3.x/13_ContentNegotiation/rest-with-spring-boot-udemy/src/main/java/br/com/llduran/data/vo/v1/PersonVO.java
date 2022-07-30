package br.com.llduran.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
public class PersonVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	private String address;

	@JsonIgnore
	private String gender;
}
