package br.com.llduran.restwithspringbootudemy.repository;

import br.com.llduran.restwithspringbootudemy.data.model.Book;
import br.com.llduran.restwithspringbootudemy.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>
{
	@Query("SELECT b FROM Book b WHERE b.title LIKE LOWER(CONCAT('%', :title, '%'))") // Para aplicação real pode não ser performático
	Page<Book> findBookByTitle(@Param("title") String title, Pageable pageable);
}