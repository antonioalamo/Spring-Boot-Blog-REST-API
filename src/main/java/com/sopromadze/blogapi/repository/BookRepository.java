package com.sopromadze.blogapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sopromadze.blogapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Page<Book> findByCreatedBy(Long userId, Pageable pageable);
}
