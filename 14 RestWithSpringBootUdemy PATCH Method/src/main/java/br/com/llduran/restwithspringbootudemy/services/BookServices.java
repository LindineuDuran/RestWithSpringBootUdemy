package br.com.llduran.restwithspringbootudemy.services;

import br.com.llduran.restwithspringbootudemy.converter.DozerConverter;
import br.com.llduran.restwithspringbootudemy.data.model.Book;
import br.com.llduran.restwithspringbootudemy.data.vo.v1.BookVO;
import br.com.llduran.restwithspringbootudemy.exception.ResourceNotFoundException;
import br.com.llduran.restwithspringbootudemy.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices
{
	@Autowired
	private BookRepository repository;

	public BookVO findById(Long id)
	{
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public List<BookVO> findAll()
	{
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
	}

	public BookVO create(BookVO book)
	{
		var entity = DozerConverter.parseObject(book, Book.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public BookVO update(Long id, BookVO book)
	{
		Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		book.setKey(entity.getId());
		var newEntity = DozerConverter.parseObject(book, Book.class);
		var vo = DozerConverter.parseObject(repository.save(newEntity), BookVO.class);

		return vo;
	}

	public void delete(Long id)
	{
		Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.deleteById(id);
	}
}