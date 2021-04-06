package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import com.nscooper.hsbc.library.vo.Customer;
import com.nscooper.hsbc.library.vo.Rental;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface RentalService {

    public Customer addCustomer(String firstName, String lastName)  throws LibraryException;
    public Customer getCustomer(String firstName, String lastName)  throws LibraryException;

    public Book addBook(String isbn, String title, String author, int quantity) throws LibraryException;
    public BookRentalFee addRentalFee(String isbn, String dailyFee) throws LibraryException;

    public Book getBook(String isbn) throws LibraryException;
    public List<BookRentalFee> getRentalFee(String isbn) throws LibraryException;
    public BookRentalFee getCurrentRentalFee(String isbn) throws LibraryException;

    public Rental rentBook(Customer customer, Book book, ZonedDateTime returnDate) throws LibraryException;
    public Rental returnBook(UUID rentalAgreementReference) throws LibraryException;

    List<Rental> getRentals(Customer customer) throws LibraryException;
}
