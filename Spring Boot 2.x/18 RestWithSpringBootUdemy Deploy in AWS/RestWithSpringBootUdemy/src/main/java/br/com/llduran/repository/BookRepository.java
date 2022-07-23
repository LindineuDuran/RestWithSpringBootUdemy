package br.com.llduran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.llduran.data.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}