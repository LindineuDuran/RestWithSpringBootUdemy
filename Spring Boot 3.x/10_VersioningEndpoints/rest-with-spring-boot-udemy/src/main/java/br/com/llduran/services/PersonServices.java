package br.com.llduran.services;

import br.com.llduran.data.vo.v1.PersonVO;
import br.com.llduran.exceptions.ResourceNotFoundException;
import br.com.llduran.mapper.DozerMapper;
import br.com.llduran.model.Person;
import br.com.llduran.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices
{
	@Autowired
	private PersonRepository repository;
	private final AtomicLong counter = new AtomicLong();

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<PersonVO> findAll()
	{
		logger.info("Finding all people!");

		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id)
	{
		logger.info("Finding one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(PersonVO person)
	{
		logger.info("Creating one person!");

		var entity = DozerMapper.parseObject(person, Person.class);

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

		return vo;
	}

	public PersonVO update(Long id, PersonVO person)
	{
		logger.info("Updating one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		var entityNew = DozerMapper.parseObject(person, Person.class);
		entityNew.setId(id);

		var vo = DozerMapper.parseObject(repository.save(entityNew), PersonVO.class);

		return vo;
	}

	public void delete(Long id)
	{
		logger.info("Deleting one person!");

		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		repository.deleteById(id);
	}
}
