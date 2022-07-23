package br.com.llduran.restwithspringbootudemy.services;

import br.com.llduran.restwithspringbootudemy.converter.DozerConverter;
import br.com.llduran.restwithspringbootudemy.data.model.Person;
import br.com.llduran.restwithspringbootudemy.data.vo.v1.PersonVO;
import br.com.llduran.restwithspringbootudemy.exception.ResourceNotFoundException;
import br.com.llduran.restwithspringbootudemy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonServices
{
	@Autowired
	private PersonRepository repository;

	public Page<PersonVO> findAll(Pageable pageable)
	{
		var page = repository.findAll(pageable);
		return page.map(this::convertToPersonVO);
	}

	public PersonVO findById(Long id)
	{
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable)
	{
		var page = repository.findPersonByName(firstName, pageable);
		return page.map(this::convertToPersonVO);
	}

	private PersonVO convertToPersonVO(Person entity)
	{
		return DozerConverter.parseObject(entity, PersonVO.class);
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

	@Transactional
	public PersonVO disablePerson(Long id)
	{
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.disablePerson(id);

		entity.setEnabled(false);
		var vo = DozerConverter.parseObject(entity, PersonVO.class);

		return vo;
	}

	public void delete(Long id)
	{
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.deleteById(id);
	}
}