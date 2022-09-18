package br.com.llduran.integrationtests.vo.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.io.Serializable;

@XmlRootElement
@Data
public class WrapperPersonVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private PersonEmbeddedVO embedded;
}
