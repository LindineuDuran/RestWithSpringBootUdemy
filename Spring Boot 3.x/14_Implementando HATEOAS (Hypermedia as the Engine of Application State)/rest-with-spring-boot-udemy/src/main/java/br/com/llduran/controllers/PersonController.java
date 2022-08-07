package br.com.llduran.controllers;

import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.data.vo.v2.PersonVOV2;
import br.com.llduran.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.llduran.util.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController
{
	@Autowired private PersonServices service;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public List<PersonVO> findAll()
	{
		return service.findAll();
	}

	@GetMapping(value = "/{id}", produces ={ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public PersonVO findById(@PathVariable(value = "id") Long id)
	{
		return service.findById(id);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
				 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public PersonVO create(@RequestBody PersonVO person)
	{
		return service.create(person);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
								 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public PersonVO update(@PathVariable(value = "id") Long id, @RequestBody PersonVO person)
	{
		return service.update(id, person);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id)
	{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}