package com.sopromadze.blogapi.payload.request;

import java.time.Instant;

import com.sopromadze.blogapi.payload.UserDateAuditPayload;

import lombok.Data;

@Data
public class BookRequest extends UserDateAuditPayload {

	private Long id;

	private String title;

	private String isbn13;

	private Long languageId;

	private Long numPages;

	private String publicationDate;

	private Long publisherId;

}
