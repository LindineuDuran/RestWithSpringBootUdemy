package br.com.llduran.repositories;

import br.com.llduran.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface BookRepository extends JpaRepository<Book, Long> {}
