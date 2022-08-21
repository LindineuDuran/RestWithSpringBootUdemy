package br.com.llduran.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
@Entity
@Table(name = "permission")
public class Permission implements GrantedAuthority, Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String description;

	@Override
	public String getAuthority()
	{
		return this.description;
	}
}
