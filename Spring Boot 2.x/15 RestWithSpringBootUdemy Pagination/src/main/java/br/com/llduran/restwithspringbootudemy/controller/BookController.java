package br.com.llduran.restwithspringbootudemy.controller;

import br.com.llduran.restwithspringbootudemy.data.vo.v1.BookVO;
import br.com.llduran.restwithspringbootudemy.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = { "Book Endpoint" })
@Tag(name = "Book Endpoint", description = "Service for Book")
@RestController
@RequestMapping("/api/book/v1")
public class BookController
{
	@Autowired
	BookServices services;

	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;

	@ApiOperation(value = "List of books recorded")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return a list of books") })
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="limit", defaultValue="12") int limit,
			@RequestParam(value="direction", defaultValue="asc") String direction)
	{
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

		Page<BookVO> bookVOS = services.findAll(pageable);
		bookVOS.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

		PagedModel<?> resources = assembler.toModel(bookVOS);

		return new ResponseEntity<>(resources, HttpStatus.OK);
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

	@ApiOperation(value = "Find all people with token name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Find all people with token name") })
	@GetMapping(value = "/findBookByTitle/{title}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findBookByTitle(@PathVariable("title") String title,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="limit", defaultValue="12") int limit,
			@RequestParam(value="direction", defaultValue="asc") String direction)
	{
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

		Page<BookVO> bookVOS = services.findBookByTitle(title, pageable);
		bookVOS.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		PagedModel<?> resources = assembler.toModel(bookVOS);

		return new ResponseEntity<>(resources, HttpStatus.OK);
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