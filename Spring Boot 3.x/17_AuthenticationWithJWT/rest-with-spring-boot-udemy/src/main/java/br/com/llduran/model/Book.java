package br.com.llduran.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "books")
public class Book implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 180)
	private String author;

	@Column(name = "launch_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date launchDate;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false, length = 250)
	private String title;
}
