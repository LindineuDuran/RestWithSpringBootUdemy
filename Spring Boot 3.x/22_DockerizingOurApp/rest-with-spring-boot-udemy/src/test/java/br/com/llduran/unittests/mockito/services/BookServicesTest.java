package br.com.llduran.unittests.mockito.services;

import br.com.llduran.data.vo.v1.BookVO;
import br.com.llduran.exceptions.RequiredObjectIsNullException;
import br.com.llduran.model.Book;
import br.com.llduran.repositories.BookRepository;
import br.com.llduran.services.BookServices;
import br.com.llduran.unittests.mapper.mocks.MockBook;
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
class BookServicesTest
{
	private MockBook input;

	@InjectMocks private BookServices service;

	@Mock private BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception
	{
		this.input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById()
	{
		final Book entity = this.input.mockEntity(1);
		entity.setId(1L);

		when(this.repository.findById(1L)).thenReturn(Optional.of(entity));

		final var result = this.service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.getLinks().toString().contains("</api/book/v1/1>;rel=\"self\""));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testCreate()
	{
		final Book entity = this.input.mockEntity(1);
		entity.setId(1L);

		final Book persisted = entity;
		persisted.setId(1L);

		final BookVO vo = this.input.mockVO(1);
		vo.setKey(1L);

		when(this.repository.save(entity)).thenReturn(persisted);

		final var result = this.service.create(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.getLinks().toString().contains("</api/book/v1/1>;rel=\"self\""));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testCreateWithNullBook()
	{
		final Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			this.service.create(null);
		});

		final String expectedMessage = "It is not allowed to persist a null object!";
		final String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate()
	{
		final Book entity = this.input.mockEntity(1);

		final Book persisted = entity;
		persisted.setId(1L);

		final BookVO vo = this.input.mockVO(1);
		vo.setKey(1L);

		when(this.repository.findById(1L)).thenReturn(Optional.of(entity));
		when(this.repository.save(entity)).thenReturn(persisted);

		final var result = this.service.update(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.getLinks().toString().contains("</api/book/v1/1>;rel=\"self\""));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testUpdateWithNullBook()
	{
		final Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			this.service.update(null);
		});

		final String expectedMessage = "It is not allowed to persist a null object!";
		final String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete()
	{
		final Book entity = this.input.mockEntity(1);
		entity.setId(1L);

		when(this.repository.findById(1L)).thenReturn(Optional.of(entity));

		this.service.delete(1L);
	}
}