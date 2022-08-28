package br.com.llduran.unittests.mockito.services;

import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.exceptions.RequiredObjectIsNullException;
import br.com.llduran.model.Person;
import br.com.llduran.repositories.PersonRepository;
import br.com.llduran.services.PersonServices;
import br.com.llduran.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest
{
	MockPerson input;

	@InjectMocks private PersonServices service;

	@Mock PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception
	{
		this.input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById()
	{
		final Person entity = this.input.mockEntity(1);
		entity.setId(1L);

		when(this.repository.findById(1L)).thenReturn(Optional.of(entity));

		final var result = this.service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.getLinks().toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreate()
	{
		final Person entity = this.input.mockEntity(1);
		entity.setId(1L);

		final Person persisted = entity;
		persisted.setId(1L);

		final PersonVO vo = this.input.mockVO(1);
		vo.setKey(1L);

		when(this.repository.save(entity)).thenReturn(persisted);

		final var result = this.service.create(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.getLinks().toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreateWithNullPerson()
	{
		final Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			this.service.create(null); });

		final String expectedMessage = "It is not allowed to persist a null object!";
		final String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate()
	{
		final Person entity = this.input.mockEntity(1);

		final Person persisted = entity;
		persisted.setId(1L);

		final PersonVO vo = this.input.mockVO(1);
		vo.setKey(1L);

		when(this.repository.findById(1L)).thenReturn(Optional.of(entity));
		when(this.repository.save(entity)).thenReturn(persisted);

		final var result = this.service.update(1L, vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.getLinks().toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testUpdateWithNullPerson()
	{
		final Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			this.service.update(null, null); });

		final String expectedMessage = "It is not allowed to persist a null object!";
		final String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete()
	{
		final Person entity = this.input.mockEntity(1);
		entity.setId(1L);

		when(this.repository.findById(1L)).thenReturn(Optional.of(entity));

		this.service.delete(1L);
	}

	@Test
	void testFindAll()
	{
		final List<Person> list = this.input.mockEntityList();

		when(this.repository.findAll()).thenReturn(list);

		final var people = this.service.findAll();

		assertNotNull(people);
		assertEquals(14, people.size());

		final var personOne = people.get(1);

		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());

		assertTrue(personOne.getLinks().toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Address Test1", personOne.getAddress());
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("Female", personOne.getGender());

		final var personFour = people.get(4);

		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());

		System.out.println(personFour.getLinks());

		assertTrue(personFour.getLinks().toString().contains("</api/person/v1/4>;rel=\"self\""));
		assertEquals("Address Test4", personFour.getAddress());
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("Male", personFour.getGender());

		final var personSeven = people.get(7);

		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());

		assertTrue(personSeven.getLinks().toString().contains("</api/person/v1/7>;rel=\"self\""));
		assertEquals("Address Test7", personSeven.getAddress());
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("Female", personSeven.getGender());
	}
}