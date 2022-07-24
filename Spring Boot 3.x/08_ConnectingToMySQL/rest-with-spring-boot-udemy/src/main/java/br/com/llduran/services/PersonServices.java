package br.com.llduran.services;

import br.com.llduran.exceptions.ResourceNotFoundException;
import br.com.llduran.model.Person;
import br.com.llduran.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices
{
	@Autowired
	private PersonRepository repository;
	private final AtomicLong counter = new AtomicLong();

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<Person> findAll()
	{
		logger.info("Finding all people!");

		return repository.findAll();
	}

	public Person findById(Long id)
	{
		logger.info("Finding one person!");

		return repository.findById(id)
				         .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}

	public Person create(Person person)
	{
		logger.info("Creating one person!");

		return repository.save(person);
	}

	public Person update(Long id, Person person)
	{
		logger.info("Updating one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		person.setId(id);

		return repository.save(person);
	}

	public void delete(Long id)
	{
		logger.info("Deleting one person!");

		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		repository.deleteById(id);
	}
}
