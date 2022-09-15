package com.sopromadze.blogapi.service;

import org.springframework.http.ResponseEntity;

import com.sopromadze.blogapi.model.Book;
import com.sopromadze.blogapi.payload.BlogApiResponse;
import com.sopromadze.blogapi.payload.BookResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.request.BookRequest;
import com.sopromadze.blogapi.security.UserPrincipal;

public interface BookService {

	PagedResponse<BookResponse> getAllBooks(int page, int size);

	ResponseEntity<Book> addBook(BookRequest bookRequest, UserPrincipal currentUser);

	ResponseEntity<Book> getBook(Long id);

	ResponseEntity<BookResponse> updateBook(Long id, BookRequest newBook, UserPrincipal currentUser);

	ResponseEntity<BlogApiResponse> deleteBook(Long id, UserPrincipal currentUser);

}
