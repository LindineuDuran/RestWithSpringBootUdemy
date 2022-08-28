package br.com.llduran.services;

import br.com.llduran.controllers.PersonController;
import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.data.vo.v2.PersonVOV2;
import br.com.llduran.exceptions.RequiredObjectIsNullException;
import br.com.llduran.exceptions.ResourceNotFoundException;
import br.com.llduran.mapper.DozerMapper;
import br.com.llduran.mapper.custom.PersonMapper;
import br.com.llduran.model.Person;
import br.com.llduran.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service public class PersonServices
{
	private final Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired PersonRepository repository;

	public List<PersonVO> findAll()
	{
		this.logger.info("Finding all people!");

		final var persons = DozerMapper.parseListObjects(this.repository.findAll(), PersonVO.class);
		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO findById(final Long id)
	{
		this.logger.info("Finding one person!");

		final var entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		final var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public PersonVO create(final PersonVO person)
	{

		if (person == null)
			throw new RequiredObjectIsNullException();

		this.logger.info("Creating one person!");
		final var entity = DozerMapper.parseObject(person, Person.class);
		final var vo = DozerMapper.parseObject(this.repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonVO update(final Long id, final PersonVO person)
	{
		if (person == null)
			throw new RequiredObjectIsNullException();

		this.logger.info("Updating one person!");

		final var entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setId(id);
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		final var vo = DozerMapper.parseObject(this.repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(final Long id)
	{
		this.logger.info("Deleting one person!");

		final var entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		this.repository.delete(entity);
	}
}