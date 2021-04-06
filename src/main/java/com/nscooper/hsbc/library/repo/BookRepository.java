package com.nscooper.hsbc.library.repo;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {

    public Book findByIsbn(String isbn) throws LibraryException;
}
