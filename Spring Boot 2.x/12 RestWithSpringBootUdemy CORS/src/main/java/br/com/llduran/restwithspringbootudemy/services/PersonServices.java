package br.com.llduran.restwithspringbootudemy.services;

import br.com.llduran.restwithspringbootudemy.converter.DozerConverter;
import br.com.llduran.restwithspringbootudemy.data.model.Person;
import br.com.llduran.restwithspringbootudemy.data.vo.v1.PersonVO;
import br.com.llduran.restwithspringbootudemy.exception.ResourceNotFoundException;
import br.com.llduran.restwithspringbootudemy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices
{
	@Autowired
	private PersonRepository repository;

	public PersonVO findById(Long id)
	{
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public List<PersonVO> findAll()
	{
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO create(PersonVO person)
	{
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public PersonVO update(Long id, PersonVO person)
	{
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		person.setKey(entity.getId());
		var newEntity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(newEntity), PersonVO.class);

		return vo;
	}

	public void delete(Long id)
	{
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.deleteById(id);
	}
}