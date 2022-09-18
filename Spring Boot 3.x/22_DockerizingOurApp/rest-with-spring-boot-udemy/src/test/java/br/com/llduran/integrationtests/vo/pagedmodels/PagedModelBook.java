package br.com.llduran.integrationtests.vo.pagedmodels;

import br.com.llduran.integrationtests.vo.BookVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement
public class PagedModelBook
{
	@XmlElement(name = "content")
	private List<BookVO> content;
}
