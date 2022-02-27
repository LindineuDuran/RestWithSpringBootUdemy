package br.com.llduran.restwithspringbootudemy.controller;

import br.com.llduran.restwithspringbootudemy.data.vo.v1.BookVO;
import br.com.llduran.restwithspringbootudemy.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "", tags = { "Book Endpoint" })
@Tag(name = "Book Endpoint", description = "Service for Book")
@RestController
@RequestMapping("/api/book/v1")
public class BookController
{
	@Autowired
	BookServices services;

	@ApiOperation(value = "List of books recorded")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return a list of books") })
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public List<BookVO> findAll()
	{
		List<BookVO> bookVOS = services.findAll();
		bookVOS.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return bookVOS;
	}

	@ApiOperation(value = "Search a book by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return a book") })
	@GetMapping(value="/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO findById(@PathVariable("id") long id)
	{
		BookVO bookVO = services.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Save a new book")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Save a new book") })
	@PostMapping(consumes = {"application/json", "application/xml", "application/x-yaml"}, produces = {"application/json", "application/xml", "application/x-yaml"})
	@ResponseStatus(HttpStatus.CREATED)
	public BookVO create(@RequestBody BookVO book)
	{
		BookVO bookVO = services.create(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Update a book")
	@PutMapping(value="/{id}", consumes = {"application/json", "application/xml", "application/x-yaml"}, produces = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO update(@PathVariable("id") Long id, @RequestBody BookVO book)
	{
		BookVO bookVO = services.update(id, book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Exclude a book")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id)
	{
		services.delete(id);

		return ResponseEntity.ok().build();
	}
}