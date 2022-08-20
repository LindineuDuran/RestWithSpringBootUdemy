package br.com.llduran.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Mapping("id")
	private Long key;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	private String address;

	private String gender;
}
