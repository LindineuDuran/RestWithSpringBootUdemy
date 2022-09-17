package br.com.llduran.integrationtests.controller.withxml;

import br.com.llduran.configs.TestConfigs;
import br.com.llduran.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.llduran.integrationtests.vo.AccountCredentialsVO;
import br.com.llduran.integrationtests.vo.BookVO;
import br.com.llduran.integrationtests.vo.TokenVO;
import br.com.llduran.integrationtests.vo.pagedmodels.PagedModelBook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerXmlTest extends AbstractIntegrationTest
{
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static BookVO book;

	@BeforeAll
	public static void setup()
	{
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		book = new BookVO();
	}

	@Test
	@Order(1)
	public void authorization()
	{
		AccountCredentialsVO user = new AccountCredentialsVO();
		user.setUsername("leandro");
		user.setPassword("admin123");

		var token =
				given()
						.basePath("/auth/signin")
							.port(TestConfigs.SERVER_PORT)
							.contentType(TestConfigs.CONTENT_TYPE_XML)
						.body(user)
							.when()
						.post()
							.then()
								.statusCode(200)
									.extract()
									.body()
										.as(TokenVO.class)
									.getAccessToken();

		specification =
				new RequestSpecBuilder()
						.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token)
						.setBasePath("/api/book/v1")
						.setPort(TestConfigs.SERVER_PORT)
						.addFilter(new RequestLoggingFilter(LogDetail.ALL))
						.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
						.build();
	}

	@Test
	@Order(2)
	public void testCreate() throws JsonMappingException, JsonProcessingException
	{
		mockBook();

		var xml = xmlSerialization(book);

		var content = given().spec(specification)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
					.body(xml)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();

		book = xmlDeserialization(content);

		assertNotNull(book.getId());
		assertNotNull(book.getTitle());
		assertNotNull(book.getAuthor());
		assertNotNull(book.getPrice());
		assertTrue(book.getId() > 0);
		assertEquals("Docker Deep Dive", book.getTitle());
		assertEquals("Nigel Poulton", book.getAuthor());
		assertEquals(55.99, book.getPrice());
	}

	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException
	{
		book.setTitle("Docker Deep Dive - Updated");

		var xml = xmlSerialization(book);

		var content = given().spec(specification)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
					.body(xml)
					.when()
					.put()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();

		BookVO bookUpdated = xmlDeserialization(content);

		assertNotNull(bookUpdated.getId());
		assertNotNull(bookUpdated.getTitle());
		assertNotNull(bookUpdated.getAuthor());
		assertNotNull(bookUpdated.getPrice());
		assertEquals(bookUpdated.getId(), book.getId());
		assertEquals("Docker Deep Dive - Updated", bookUpdated.getTitle());
		assertEquals("Nigel Poulton", bookUpdated.getAuthor());
		assertEquals(55.99, bookUpdated.getPrice());
	}

	@Test
	@Order(4)
	public void testFindById() throws JsonMappingException, JsonProcessingException
	{
		mockBook();

		var content = given().spec(specification)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("id", book.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();

		BookVO foundBook = xmlDeserialization(content);

		assertNotNull(foundBook.getId());
		assertNotNull(foundBook.getTitle());
		assertNotNull(foundBook.getAuthor());
		assertNotNull(foundBook.getPrice());
		assertEquals(foundBook.getId(), book.getId());
		assertEquals("Docker Deep Dive - Updated", foundBook.getTitle());
		assertEquals("Nigel Poulton", foundBook.getAuthor());
		assertEquals(55.99, foundBook.getPrice());
	}

	@Test
	@Order(5)
	public void testDelete()
	{
		given().spec(specification)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("id", book.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
	}

	@Test
	@Order(6)
	public void testFindAll() throws JsonMappingException, JsonProcessingException
	{
		var content = given().spec(specification)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0 , "limit", 5, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
					.extract()
					.body()
						.asString();

		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		PagedModelBook wrapper = xmlMapper.readValue(content, PagedModelBook.class);
		var books = wrapper.getContent();

		BookVO foundBookOne = books.get(0);

		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getTitle());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getPrice());
		assertTrue(foundBookOne.getId() > 0);
		assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", foundBookOne.getTitle());
		assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", foundBookOne.getAuthor());
		assertEquals(54.00, foundBookOne.getPrice());

		BookVO foundBookFive = books.get(4);

		assertNotNull(foundBookFive.getId());
		assertNotNull(foundBookFive.getTitle());
		assertNotNull(foundBookFive.getAuthor());
		assertNotNull(foundBookFive.getPrice());
		assertTrue(foundBookFive.getId() > 0);
		assertEquals("Domain Driven Design", foundBookFive.getTitle());
		assertEquals("Eric Evans", foundBookFive.getAuthor());
		assertEquals(92.0, foundBookFive.getPrice());
	}

	@Test
	@Order(7)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException
	{
		var content = given().spec(specification)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0 , "limit", 5, "direction", "asc")
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/book/v1/12</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/book/v1/8</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/book/v1/15</href></links>"));

		assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/book/v1?direction=asc&amp;page=0&amp;size=10&amp;sort=title,asc</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/book/v1?page=0&amp;size=10&amp;direction=asc</href></links>"));
		assertTrue(content.contains("<links><rel>next</rel><href>http://localhost:8888/api/book/v1?direction=asc&amp;page=1&amp;size=10&amp;sort=title,asc</href></links>"));
		assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/book/v1?direction=asc&amp;page=1&amp;size=10&amp;sort=title,asc"));

		assertTrue(content.contains("<page><size>10</size><totalElements>15</totalElements><totalPages>2</totalPages><number>0</number></page>"));
	}

	private void mockBook()
	{
		book.setTitle("Docker Deep Dive");
		book.setAuthor("Nigel Poulton");
		book.setPrice(Double.valueOf(55.99));
		book.setLaunchDate(new Date());
	}

	private String xmlSerialization(BookVO objBook) throws JsonProcessingException
	{
		/*XML Serialization and Deserialization with Jackson
		https://www.baeldung.com/jackson-xml-serialization-and-deserialization*/
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.writeValueAsString(objBook);
	}

	private BookVO xmlDeserialization(String content) throws JsonProcessingException
	{
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return xmlMapper.readValue(content, BookVO.class);
	}
}