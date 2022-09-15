package com.sopromadze.blogapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopromadze.blogapi.exception.ResponseEntityErrorException;
import com.sopromadze.blogapi.model.Book;
import com.sopromadze.blogapi.payload.BlogApiResponse;
import com.sopromadze.blogapi.payload.BookResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.request.BookRequest;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.BookService;
import com.sopromadze.blogapi.utils.AppConstants;
import com.sopromadze.blogapi.utils.AppUtils;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	private BookService bookService;

	@ExceptionHandler(ResponseEntityErrorException.class)
	public ResponseEntity<BlogApiResponse> handleExceptions(ResponseEntityErrorException exception) {
		return exception.getApiResponse();
	}

	@GetMapping()
	// @ApiOperation(value = "Book List")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request" ) })
	public PagedResponse<BookResponse> getAllBooks(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		AppUtils.validatePageNumberAndSize(page, size);

		return bookService.getAllBooks(page, size);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Book> addBook(@Valid @RequestBody BookRequest bookRequest, @CurrentUser UserPrincipal currentUser) {
		return bookService.addBook(bookRequest, currentUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable(name = "id") Long id) {
		return bookService.getBook(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<BookResponse> updateBook(@PathVariable(name = "id") Long id, @Valid @RequestBody BookRequest newBook,
			@CurrentUser UserPrincipal currentUser) {
		return bookService.updateBook(id, newBook, currentUser);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<BlogApiResponse> deleteBook(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
		return bookService.deleteBook(id, currentUser);
	}

}
