package br.com.llduran.services;

import br.com.llduran.controllers.BookController;
import br.com.llduran.controllers.PersonController;
import br.com.llduran.data.vo.v1.BookVO;
import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.exceptions.RequiredObjectIsNullException;
import br.com.llduran.exceptions.ResourceNotFoundException;
import br.com.llduran.mapper.DozerMapper;
import br.com.llduran.model.Book;
import br.com.llduran.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices
{
	private final Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired
	private BookRepository repository;

	@Autowired
	PagedResourcesAssembler<BookVO> assembler;

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable)
	{
		this.logger.info("Finding all book!");

		var bookPage = repository.findAll(pageable);

		var bookVOsPage = bookPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
		bookVOsPage.map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

		Link link = linkTo(methodOn(BookController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();

		return assembler.toModel(bookVOsPage, link);
	}

	public BookVO findById(final Long id)
	{

		this.logger.info("Finding one book!");

		final var entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		final var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}

	public BookVO create(final BookVO book)
	{
		if (book == null)
			throw new RequiredObjectIsNullException();

		this.logger.info("Creating one book!");
		final var entity = DozerMapper.parseObject(book, Book.class);

		final var persisted = this.repository.save(entity);
		final var vo = DozerMapper.parseObject(this.repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public BookVO update(final BookVO book)
	{

		if (book == null)
			throw new RequiredObjectIsNullException();

		this.logger.info("Updating one book!");

		final var entity = this.repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());

		final var vo = DozerMapper.parseObject(this.repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(final Long id)
	{
		this.logger.info("Deleting one book!");

		final var entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		this.repository.delete(entity);
	}
}
