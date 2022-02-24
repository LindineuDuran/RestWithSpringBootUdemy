package br.com.llduran.converter;

import br.com.llduran.converter.mocks.MockPerson;
import br.com.llduran.data.model.Person;
import br.com.llduran.data.vo.PersonVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class DozerConverterTest
{
	MockPerson inputObject;

	@Test
	public void parseEntityToVOTest()
	{
		inputObject = new MockPerson();
		PersonVO output = DozerConverter.parseObject(inputObject.mockEntity(), PersonVO.class);
		Assertions.assertEquals(Long.valueOf(0L), output.getId());
		Assertions.assertEquals("First Name Test0", output.getFirstName());
		Assertions.assertEquals("Last Name Test0", output.getLastName());
		Assertions.assertEquals("Addres Test0", output.getAddress());
		Assertions.assertEquals("Male", output.getGender());
	}

	@Test
	public void parseEntityListToVOListTest()
	{
		inputObject = new MockPerson();
		List<PersonVO> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), PersonVO.class);
		PersonVO outputZero = outputList.get(0);

		Assertions.assertEquals(Long.valueOf(0L), outputZero.getId());
		Assertions.assertEquals("First Name Test0", outputZero.getFirstName());
		Assertions.assertEquals("Last Name Test0", outputZero.getLastName());
		Assertions.assertEquals("Addres Test0", outputZero.getAddress());
		Assertions.assertEquals("Male", outputZero.getGender());

		PersonVO outputSeven = outputList.get(7);

		Assertions.assertEquals(Long.valueOf(7L), outputSeven.getId());
		Assertions.assertEquals("First Name Test7", outputSeven.getFirstName());
		Assertions.assertEquals("Last Name Test7", outputSeven.getLastName());
		Assertions.assertEquals("Addres Test7", outputSeven.getAddress());
		Assertions.assertEquals("Female", outputSeven.getGender());

		PersonVO outputTwelve = outputList.get(12);

		Assertions.assertEquals(Long.valueOf(12L), outputTwelve.getId());
		Assertions.assertEquals("First Name Test12", outputTwelve.getFirstName());
		Assertions.assertEquals("Last Name Test12", outputTwelve.getLastName());
		Assertions.assertEquals("Addres Test12", outputTwelve.getAddress());
		Assertions.assertEquals("Male", outputTwelve.getGender());
	}

	@Test
	public void parseVOToEntityTest()
	{
		inputObject = new MockPerson();
		Person output = DozerConverter.parseObject(inputObject.mockVO(), Person.class);
		Assertions.assertEquals(Long.valueOf(0L), output.getId());
		Assertions.assertEquals("First Name Test0", output.getFirstName());
		Assertions.assertEquals("Last Name Test0", output.getLastName());
		Assertions.assertEquals("Addres Test0", output.getAddress());
		Assertions.assertEquals("Male", output.getGender());
	}

	@Test
	public void parserVOListToEntityListTest()
	{
		inputObject = new MockPerson();
		List<Person> outputList = DozerConverter.parseListObjects(inputObject.mockVOList(), Person.class);
		Person outputZero = outputList.get(0);

		Assertions.assertEquals(Long.valueOf(0L), outputZero.getId());
		Assertions.assertEquals("First Name Test0", outputZero.getFirstName());
		Assertions.assertEquals("Last Name Test0", outputZero.getLastName());
		Assertions.assertEquals("Addres Test0", outputZero.getAddress());
		Assertions.assertEquals("Male", outputZero.getGender());

		Person outputSeven = outputList.get(7);

		Assertions.assertEquals(Long.valueOf(7L), outputSeven.getId());
		Assertions.assertEquals("First Name Test7", outputSeven.getFirstName());
		Assertions.assertEquals("Last Name Test7", outputSeven.getLastName());
		Assertions.assertEquals("Addres Test7", outputSeven.getAddress());
		Assertions.assertEquals("Female", outputSeven.getGender());

		Person outputTwelve = outputList.get(12);

		Assertions.assertEquals(Long.valueOf(12L), outputTwelve.getId());
		Assertions.assertEquals("First Name Test12", outputTwelve.getFirstName());
		Assertions.assertEquals("Last Name Test12", outputTwelve.getLastName());
		Assertions.assertEquals("Addres Test12", outputTwelve.getAddress());
		Assertions.assertEquals("Male", outputTwelve.getGender());
	}
}
