package br.com.llduran.services;

import br.com.llduran.exception.ExceptionResponse;
import br.com.llduran.exception.ResourceNotFoundException;
import br.com.llduran.model.Person;
import br.com.llduran.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServices
{
	@Autowired
	private PersonRepository repository;

	public Person findById(Long id)
	{
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}

	public List<Person> findAll()
	{
		return repository.findAll();
	}

	public Person create(Person person)
	{
		return repository.save(person);
	}

	public Person update(Long id, Person person)
	{
		Person personVelho = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		person.setId(personVelho.getId());

		return repository.save(person);
	}

	public void delete(Long id)
	{
		Person personVelho = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.deleteById(id);
	}
}
