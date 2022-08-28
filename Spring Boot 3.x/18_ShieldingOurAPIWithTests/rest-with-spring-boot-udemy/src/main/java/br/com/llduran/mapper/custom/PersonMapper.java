package br.com.llduran.mapper.custom;

import br.com.llduran.data.vo.v2.PersonVOV2;
import br.com.llduran.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper
{
	public PersonVOV2 convertEntityToVo(final Person person)
	{
		final PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		return vo;
	}

	public Person convertVoTOEntity(final PersonVOV2 person)
	{
		final Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		//vo.setBirthDay(new Date());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}
}
