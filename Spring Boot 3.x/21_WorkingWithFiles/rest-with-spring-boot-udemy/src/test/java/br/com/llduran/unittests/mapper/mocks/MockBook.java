package br.com.llduran.unittests.mapper.mocks;

import br.com.llduran.data.vo.v1.BookVO;
import br.com.llduran.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook
{
	public Book mockEntity()
	{
		return this.mockEntity(0);
	}

	public BookVO mockVO()
	{
		return this.mockVO(0);
	}

	public List<Book> mockEntityList()
	{
		final List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < 14; i++)
		{
			books.add(this.mockEntity(i));
		}
		return books;
	}

	public List<BookVO> mockVOList()
	{
		final List<BookVO> books = new ArrayList<>();
		for (int i = 0; i < 14; i++)
		{
			books.add(this.mockVO(i));
		}
		return books;
	}

	public Book mockEntity(final Integer number)
	{
		final Book book = new Book();
		book.setId(number.longValue());
		book.setAuthor("Some Author" + number);
		book.setLaunchDate(new Date());
		book.setPrice(25D);
		book.setTitle("Some Title" + number);
		return book;
	}

	public BookVO mockVO(final Integer number)
	{
		final BookVO book = new BookVO();
		book.setKey(number.longValue());
		book.setAuthor("Some Author" + number);
		book.setLaunchDate(new Date());
		book.setPrice(25D);
		book.setTitle("Some Title" + number);
		return book;
	}
}