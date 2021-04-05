package com.nscooper.hsbc.library.repo;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Book;

public interface BookRepository {

    public Book addBook(Book book) throws LibraryException;
    public Book updateBook(Book book) throws LibraryException;
    public Book getBook(String isbn) throws LibraryException;
}
