package com.sopromadze.blogapi.exception;

import com.sopromadze.blogapi.payload.BlogApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private BlogApiResponse apiResponse;

	private String message;

	public AccessDeniedException(BlogApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public AccessDeniedException(String message) {
		super(message);
		this.message = message;
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlogApiResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(BlogApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
