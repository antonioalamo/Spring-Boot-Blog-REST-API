package com.sopromadze.blogapi.exception;

import com.sopromadze.blogapi.payload.BlogApiResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityErrorException extends RuntimeException {
	private static final long serialVersionUID = -3156815846745801694L;

	private transient ResponseEntity<BlogApiResponse> apiResponse;

	public ResponseEntityErrorException(ResponseEntity<BlogApiResponse> apiResponse) {
		this.apiResponse = apiResponse;
	}

	public ResponseEntity<BlogApiResponse> getApiResponse() {
		return apiResponse;
	}
}
