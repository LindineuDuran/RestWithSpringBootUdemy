package br.com.llduran.controller;

import br.com.llduran.exception.UnsuportedMathOperationException;
import br.com.llduran.math.SimpleMath;
import br.com.llduran.model.Person;
import br.com.llduran.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.llduran.converters.NumberConverter.isNumeric;

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
	public Person findById(@PathVariable("id") String id)
	{
		return services.findById(id);
	}

	@RequestMapping(method= RequestMethod.POST,
			        consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person)
	{
		return services.create(person);
	}

	@RequestMapping(value="/{id}",
					method= RequestMethod.PUT,
			        consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@PathVariable("id") String id, @RequestBody Person person)
	{
		return services.update(person);
	}

	@RequestMapping(value="/{id}",
					method= RequestMethod.DELETE)
	public void delete(@PathVariable("id") String id)
	{
		services.delete(id);
	}
}