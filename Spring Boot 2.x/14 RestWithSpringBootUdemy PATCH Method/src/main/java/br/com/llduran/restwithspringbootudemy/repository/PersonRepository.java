package br.com.llduran.restwithspringbootudemy.repository;

import br.com.llduran.restwithspringbootudemy.data.model.Person;
import br.com.llduran.restwithspringbootudemy.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>
{
	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
	void disablePerson(@Param("id") Long id);
}