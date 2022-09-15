package com.sopromadze.blogapi.payload;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(Include.NON_NULL)
public class BookResponse extends UserDateAuditPayload {
	private Long id;

	private String title;

	private String isbn13;

	private Long languageId;

	private Long numPages;

	private String publicationDate;

	private Long publisherId;

}
