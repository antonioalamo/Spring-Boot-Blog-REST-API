package com.sopromadze.blogapi.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.sopromadze.blogapi.model.audit.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "books", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Book extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "title")
	private String title;

	@Column(name = "isbn13")
	private String isbn13;

	@Column(name = "language_id")
	private Long languageId;

	@Column(name = "num_pages")
	private Long numPages;

	@Column(name = "publication_date")
	private String publicationDate;

	@Column(name = "publisher_id")
	private Long publisherId;

}
