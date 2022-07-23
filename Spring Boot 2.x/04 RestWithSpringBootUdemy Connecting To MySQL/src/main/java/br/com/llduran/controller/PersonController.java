package br.com.llduran.controller;

import br.com.llduran.model.Person;
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
	public List<Person> findAll()
	{
		return services.findAll();
	}

	@RequestMapping(value="/{id}",
			        method= RequestMethod.GET,
	                produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable("id") long id)
	{
		return services.findById(id);
	}

	@RequestMapping(method= RequestMethod.POST)
	public Person create(@RequestBody Person person)
	{
		return services.create(person);
	}

	@RequestMapping(value="/{id}", method= RequestMethod.PUT)
	public Person update(@PathVariable("id") Long id, @RequestBody Person person)
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