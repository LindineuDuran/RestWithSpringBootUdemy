package br.com.llduran.restwithspringbootudemy.repository;

import br.com.llduran.restwithspringbootudemy.data.model.Book;
import br.com.llduran.restwithspringbootudemy.data.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>
{
}