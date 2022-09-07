package br.com.llduran.integrationtests.controller.withjson;

import br.com.llduran.configs.TestConfigs;
import br.com.llduran.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.llduran.integrationtests.vo.AccountCredentialsVO;
import br.com.llduran.integrationtests.vo.BookVO;
import br.com.llduran.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.apache.tomcat.util.http.parser.Host.parse;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerCorsJsonTest extends AbstractIntegrationTest
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
	@Order(0)
	public void authorization() throws JsonProcessingException
	{
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		var accessToken = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(TokenVO.class)
								.getAccessToken();

		BookControllerCorsJsonTest.specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonProcessingException
	{
		this.mockBook();

		var content = given().spec(BookControllerCorsJsonTest.specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.body(BookControllerCorsJsonTest.book)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;

		assertNotNull(persistedBook);

		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());

		assertTrue(persistedBook.getId() > 0);

		assertEquals("Isaac Azimov", persistedBook.getAuthor());
		assertEquals(89.0, persistedBook.getPrice());
		assertEquals("A Fundação", persistedBook.getTitle());
	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonProcessingException
	{
		this.mockBook();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
				.body(book)
				.when()
				.post()
				.then()
				.statusCode(403)
				.extract()
				.body()
				.asString();

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonProcessingException
	{
		this.mockBook();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.pathParam("id", book.getId())
				.when()
				.get("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;

		assertNotNull(persistedBook);

		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());

		assertTrue(persistedBook.getId() > 0);

		assertEquals("Isaac Azimov", persistedBook.getAuthor());
		assertEquals(89.0, persistedBook.getPrice());
		assertEquals("A Fundação", persistedBook.getTitle());
	}

	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonProcessingException
	{
		this.mockBook();

		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
				.setBasePath("/api/book/v1").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", book.getId()).when().get("{id}").then().statusCode(403).extract().body().asString();

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	private void mockBook()
	{
		book.setAuthor("Isaac Azimov");

		Date launchDate = new Date(parse("2017-11-29T02:00:00.000+00:00"));
		book.setLaunchDate(launchDate);

		book.setPrice(89.0);
		book.setTitle("A Fundação");
	}
}