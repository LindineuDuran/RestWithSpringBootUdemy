package br.com.llduran.restwithspringbootudemy.data.vo.v1;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonPropertyOrder({ "id", "author", "title", "price", "launch_date" })
public class BookVO extends RepresentationModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	private String author;

	@JsonProperty("launch_date")
	private Date launchDate;

	private Double price;

	private String title;
}
