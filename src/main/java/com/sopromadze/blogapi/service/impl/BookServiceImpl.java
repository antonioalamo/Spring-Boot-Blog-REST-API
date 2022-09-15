package com.sopromadze.blogapi.service.impl;

import static com.sopromadze.blogapi.utils.AppConstants.ID;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.sopromadze.blogapi.exception.BlogapiException;
import com.sopromadze.blogapi.exception.ResourceNotFoundException;
import com.sopromadze.blogapi.model.Book;
import com.sopromadze.blogapi.model.role.RoleName;
import com.sopromadze.blogapi.payload.BlogApiResponse;
import com.sopromadze.blogapi.payload.BookResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.request.BookRequest;
import com.sopromadze.blogapi.repository.BookRepository;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.BookService;
import com.sopromadze.blogapi.utils.AppUtils;

@Service
public class BookServiceImpl implements BookService {
	private static final String CREATED_AT = "createdAt";

	private static final String ALBUM_STR = "Book";

	private static final String YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION = "You don't have permission to make this operation";

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PagedResponse<BookResponse> getAllBooks(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Book> books = bookRepository.findAll(pageable);

		if (books.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), books.getNumber(), books.getSize(), books.getTotalElements(),
					books.getTotalPages(), books.isLast());
		}

		List<BookResponse> bookResponses = Arrays.asList(modelMapper.map(books.getContent(), BookResponse[].class));

		return new PagedResponse<>(bookResponses, books.getNumber(), books.getSize(), books.getTotalElements(), books.getTotalPages(),
				books.isLast());
	}

	@Override
	public ResponseEntity<Book> addBook(BookRequest bookRequest, UserPrincipal currentUser) {

		Book book = new Book();

		modelMapper.map(bookRequest, book);

		Book newBook = bookRepository.save(book);
		return new ResponseEntity<>(newBook, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Book> getBook(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<BookResponse> updateBook(Long id, BookRequest newBook, UserPrincipal currentUser) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
		
		if (currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			book.setTitle(newBook.getTitle());
			Book updatedBook = bookRepository.save(book);

			BookResponse bookResponse = new BookResponse();

			modelMapper.map(updatedBook, bookResponse);

			return new ResponseEntity<>(bookResponse, HttpStatus.OK);
		}

		throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
	}

	@Override
	public ResponseEntity<BlogApiResponse> deleteBook(Long id, UserPrincipal currentUser) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
		if (currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(new BlogApiResponse(Boolean.TRUE, "You successfully deleted book"), HttpStatus.OK);
		}

		throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
	}

}
