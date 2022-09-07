package br.com.llduran.services;

import br.com.llduran.controllers.PersonController;
import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.exceptions.RequiredObjectIsNullException;
import br.com.llduran.exceptions.ResourceNotFoundException;
import br.com.llduran.mapper.DozerMapper;
import br.com.llduran.model.Person;
import br.com.llduran.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service public class PersonServices
{
	private final Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired PersonRepository repository;

	public List<PersonVO> findAll()
	{
		logger.info("Finding all people!");

		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO findById(Long id)
	{
		logger.info("Finding one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public PersonVO create(PersonVO person)
	{

		if (person == null)
			throw new RequiredObjectIsNullException();

		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonVO update(Long id, PersonVO person)
	{
		if (person == null)
			throw new RequiredObjectIsNullException();

		logger.info("Updating one person!");

		final var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setId(id);
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(this.repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	@Transactional
	public PersonVO disablePerson(Long id)
	{
		logger.info("Disabling one person!");

		repository.disablePerson(id);

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		var vo = DozerMapper.parseObject(entity, PersonVO.class);

		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public void delete(Long id)
	{
		logger.info("Deleting one person!");

		var entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}