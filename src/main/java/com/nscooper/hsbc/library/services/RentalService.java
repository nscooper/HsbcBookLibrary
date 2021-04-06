package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import org.json.JSONObject;

import java.util.List;

public interface RentalService {

    public Book addBook(String isbn, String title, String author, int quantity) throws LibraryException;
    public BookRentalFee addRentalFee(String isbn, String dailyFee) throws LibraryException;

    public Book getBook(String isbn) throws LibraryException;
    public List<BookRentalFee> getRentalFee(String isbn) throws LibraryException;

    public void rentBook() throws LibraryException;
    public void returnBook() throws LibraryException;
    public void calculateLateBorrowersFees() throws LibraryException;

}
