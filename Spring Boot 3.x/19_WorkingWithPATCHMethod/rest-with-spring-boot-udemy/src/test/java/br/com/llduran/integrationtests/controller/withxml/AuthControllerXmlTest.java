package br.com.llduran.integrationtests.controller.withxml;

import br.com.llduran.configs.TestConfigs;
import br.com.llduran.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.llduran.integrationtests.vo.AccountCredentialsVO;
import br.com.llduran.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest
{
	private static TokenVO tokenVO;

	@Test
	@Order(1)
	public void testSignin() throws JsonProcessingException
	{

		final AccountCredentialsVO user =
				new AccountCredentialsVO("leandro", "admin123");

		AuthControllerXmlTest.tokenVO = given()
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
				.as(TokenVO.class);

		assertNotNull(AuthControllerXmlTest.tokenVO.getAccessToken());
		assertNotNull(AuthControllerXmlTest.tokenVO.getRefreshToken());
	}

	@Test
	@Order(2)
	public void testRefresh() throws JsonProcessingException {

		final var newTokenVO = given()
				.basePath("/auth/refresh")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("username", AuthControllerXmlTest.tokenVO.getUsername())
				.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + AuthControllerXmlTest.tokenVO.getRefreshToken())
				.when()
				.put("{username}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class);

		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
}