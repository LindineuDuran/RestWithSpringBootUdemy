package br.com.llduran.controller;

import br.com.llduran.data.vo.PersonVO;
import br.com.llduran.data.vo.v2.PersonVOV2;
import br.com.llduran.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController
{
	@Autowired
	PersonServices services;

	@RequestMapping(method= RequestMethod.GET,
	                produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonVO> findAll()
	{
		return services.findAll();
	}

	@RequestMapping(value="/{id}",
			        method= RequestMethod.GET,
	                produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVO findById(@PathVariable("id") long id)
	{
		return services.findById(id);
	}

	@PostMapping
	public PersonVO create(@RequestBody PersonVO person) { return services.create(person); }

	@PostMapping("/v2")
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) { return services.createV2(person); }

	@RequestMapping(value="/{id}", method= RequestMethod.PUT)
	public PersonVO update(@PathVariable("id") Long id, @RequestBody PersonVO person)
	{
		return services.update(id, person);
	}

	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id)
	{
		services.delete(id);

		return ResponseEntity.ok().build();
	}
}