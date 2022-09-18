package br.com.llduran.unittests.mapper.mocks;

import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MockPerson
{
	public Person mockEntity()
	{
		return this.mockEntity(0);
	}

	public PersonVO mockVO()
	{
		return this.mockVO(0);
	}

	public List<Person> mockEntityList()
	{
		final List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 14; i++)
		{
			persons.add(this.mockEntity(i));
		}
		return persons;
	}

	public List<PersonVO> mockVOList()
	{
		final List<PersonVO> persons = new ArrayList<>();
		for (int i = 0; i < 14; i++)
		{
			persons.add(this.mockVO(i));
		}
		return persons;
	}

	public Person mockEntity(final Integer number)
	{
		final Person person = new Person();
		person.setAddress("Address Test" + number);
		person.setFirstName("First Name Test" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		person.setId(number.longValue());
		person.setLastName("Last Name Test" + number);
		return person;
	}

	public PersonVO mockVO(final Integer number)
	{
		final PersonVO person = new PersonVO();
		person.setAddress("Address Test" + number);
		person.setFirstName("First Name Test" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		person.setKey(number.longValue());
		person.setLastName("Last Name Test" + number);
		return person;
	}
}
