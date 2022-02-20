package br.com.llduran.services;

import br.com.llduran.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices
{
	private final AtomicLong counter = new AtomicLong();

	public Person findById(String id)
	{
		Person person = Person.builder()
				.id(counter.incrementAndGet())
				.firstName("Lindineu")
				.lastName("Duran")
				.address("São José dos Campos - São Paulo - Brasil")
				.gender("Male")
				.build();
		return person;
	}

	public List<Person> findAll()
	{
		List<Person> persons = new ArrayList<>();
		for(int i = 0; i < 8; i++)
		{
			Person person = mockPerson(i);
			persons.add(person);
		}

		return persons;
	}

	public Person create(Person person)
	{
		return person;
	}

	public Person update(Person person)
	{
		return person;
	}

	public void delete(String id)
	{

	}

	private Person mockPerson(int i)
	{
				Person person = Person.builder()
						.id(counter.incrementAndGet())
						.firstName("Person Name " + i)
						.lastName("Last Name " + 1)
						.address("Some Address in Brazil " + 1)
						.gender("Male")
						.build();
				return person;
	}
}
