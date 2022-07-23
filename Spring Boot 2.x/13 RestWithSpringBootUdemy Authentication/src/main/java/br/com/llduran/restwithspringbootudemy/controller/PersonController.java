package br.com.llduran.restwithspringbootudemy.controller;

import br.com.llduran.restwithspringbootudemy.data.vo.v1.PersonVO;
import br.com.llduran.restwithspringbootudemy.services.PersonServices;
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

@Api(value = "", tags = { "Person Endpoint" })
@Tag(name = "Person Endpoint", description = "Service for Person")
@RestController
@RequestMapping("/api/person/v1")
public class PersonController
{
	@Autowired
	PersonServices services;

	@ApiOperation(value = "List of people recorded")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return a list of people") })
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<PersonVO> findAll()
	{
		List<PersonVO> personVOS = services.findAll();
		personVOS.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return personVOS;
	}

	@ApiOperation(value = "Search a person by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return a person") })
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO findById(
			@PathVariable("id")
					long id)
	{
		PersonVO personVO = services.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	@ApiOperation(value = "Save a new person")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Save a new person") })
	@PostMapping(consumes = { "application/json", "application/xml", "application/x-yaml" }, produces = {
			"application/json", "application/xml", "application/x-yaml" })
	@ResponseStatus(HttpStatus.CREATED)
	public PersonVO create(@RequestBody PersonVO person)
	{
		PersonVO personVO = services.create(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	@ApiOperation(value = "Update a person")
	@PutMapping(value = "/{id}", consumes = { "application/json", "application/xml",
			"application/x-yaml" }, produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(
			@PathVariable("id")
					Long id,
			@RequestBody
					PersonVO person)
	{
		PersonVO personVO = services.update(id, person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	@ApiOperation(value = "Exclude a person")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(
			@PathVariable("id")
					Long id)
	{
		services.delete(id);

		return ResponseEntity.ok().build();
	}
}