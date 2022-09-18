package br.com.llduran.unittests.mapper;

import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.mapper.DozerMapper;
import br.com.llduran.model.Person;
import br.com.llduran.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DozerConverterTest
{
	MockPerson inputObject;

	@BeforeEach
	public void setUp()
	{
		this.inputObject = new MockPerson();
	}

	@Test
	public void parseEntityToVOTest()
	{
		final PersonVO output = DozerMapper.parseObject(this.inputObject.mockEntity(), PersonVO.class);
		assertEquals(Long.valueOf(0L), output.getKey());
		assertEquals("First Name Test0", output.getFirstName());
		assertEquals("Last Name Test0", output.getLastName());
		assertEquals("Address Test0", output.getAddress());
		assertEquals("Male", output.getGender());
	}

	@Test
	public void parseEntityListToVOListTest()
	{
		final List<PersonVO> outputList = DozerMapper.parseListObjects(this.inputObject.mockEntityList(), PersonVO.class);
		final PersonVO outputZero = outputList.get(0);

		assertEquals(Long.valueOf(0L), outputZero.getKey());
		assertEquals("First Name Test0", outputZero.getFirstName());
		assertEquals("Last Name Test0", outputZero.getLastName());
		assertEquals("Address Test0", outputZero.getAddress());
		assertEquals("Male", outputZero.getGender());

		final PersonVO outputSeven = outputList.get(7);

		assertEquals(Long.valueOf(7L), outputSeven.getKey());
		assertEquals("First Name Test7", outputSeven.getFirstName());
		assertEquals("Last Name Test7", outputSeven.getLastName());
		assertEquals("Address Test7", outputSeven.getAddress());
		assertEquals("Female", outputSeven.getGender());

		final PersonVO outputTwelve = outputList.get(12);

		assertEquals(Long.valueOf(12L), outputTwelve.getKey());
		assertEquals("First Name Test12", outputTwelve.getFirstName());
		assertEquals("Last Name Test12", outputTwelve.getLastName());
		assertEquals("Address Test12", outputTwelve.getAddress());
		assertEquals("Male", outputTwelve.getGender());
	}

	@Test
	public void parseVOToEntityTest()
	{
		final Person output = DozerMapper.parseObject(this.inputObject.mockVO(), Person.class);
		assertEquals(Long.valueOf(0L), output.getId());
		assertEquals("First Name Test0", output.getFirstName());
		assertEquals("Last Name Test0", output.getLastName());
		assertEquals("Address Test0", output.getAddress());
		assertEquals("Male", output.getGender());
	}

	@Test
	public void parserVOListToEntityListTest()
	{
		final List<Person> outputList = DozerMapper.parseListObjects(this.inputObject.mockVOList(), Person.class);
		final Person outputZero = outputList.get(0);

		assertEquals(Long.valueOf(0L), outputZero.getId());
		assertEquals("First Name Test0", outputZero.getFirstName());
		assertEquals("Last Name Test0", outputZero.getLastName());
		assertEquals("Address Test0", outputZero.getAddress());
		assertEquals("Male", outputZero.getGender());

		final Person outputSeven = outputList.get(7);

		assertEquals(Long.valueOf(7L), outputSeven.getId());
		assertEquals("First Name Test7", outputSeven.getFirstName());
		assertEquals("Last Name Test7", outputSeven.getLastName());
		assertEquals("Address Test7", outputSeven.getAddress());
		assertEquals("Female", outputSeven.getGender());

		final Person outputTwelve = outputList.get(12);

		assertEquals(Long.valueOf(12L), outputTwelve.getId());
		assertEquals("First Name Test12", outputTwelve.getFirstName());
		assertEquals("Last Name Test12", outputTwelve.getLastName());
		assertEquals("Address Test12", outputTwelve.getAddress());
		assertEquals("Male", outputTwelve.getGender());
	}
}