package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.repo.BookRentalFeeRepository;
import com.nscooper.hsbc.library.repo.BookRepository;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class RentalServiceImpl implements RentalService{

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookRentalFeeRepository bookRentalFeeRepository;

    @Override
    public Book addBook(String isbn, String title, String author, int quantity) throws LibraryException {

        Book book = bookRepository.findByIsbn(isbn);
        if (book==null) {
            book = new Book();
            book.setId(UUID.randomUUID());
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setTotalStockedCopies(quantity);
        } else {
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setTotalStockedCopies(book.getTotalStockedCopies()+quantity);
        }
        bookRepository.save(book);
        return book;

    }


    @Override
    public void rentBook() throws LibraryException {

    }

    @Override
    public void returnBook() throws LibraryException {

    }

    @Override
    public void calculateLateBorrowersFees() throws LibraryException {

    }

    @Override
    public Book getBook(String isbn) throws LibraryException {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<BookRentalFee> getRentalFee(String isbn) throws LibraryException {
        Book book = bookRepository.findByIsbn(isbn);
        if (book==null){
            throw new LibraryException(String.format("No book found with ISBN:%s.", isbn));
        }
        return bookRentalFeeRepository.findByBook(book);
    }

    @Override
    public BookRentalFee addRentalFee(String isbn, String dailyFee) throws LibraryException {

        Book book = bookRepository.findByIsbn(isbn);
        if (book==null){
            throw new LibraryException(String.format("No book found with ISBN:%s so cannot add a book fee.", isbn));
        }

        BookRentalFee bookRentalFee = bookRentalFeeRepository.findByBook(book)
                .stream()
                .filter(fee -> fee.getFeeEndDate()==null)
                .findAny()
                .orElse(null);

        if (bookRentalFee==null) {
            bookRentalFee = new BookRentalFee();
            bookRentalFee.setId(UUID.randomUUID());
            bookRentalFee.setBook(book);
            bookRentalFee.setFeeStartDate(ZonedDateTime.now());
            bookRentalFee.setFeeEndDate(null);
        } else {
            bookRentalFee.setBook(book);
            bookRentalFee.setFeeEndDate(ZonedDateTime.now());
            bookRentalFeeRepository.save(bookRentalFee);

            bookRentalFee = new BookRentalFee();
            bookRentalFee.setId(UUID.randomUUID());
            bookRentalFee.setBook(book);
            bookRentalFee.setFeeStartDate(ZonedDateTime.now());
            bookRentalFee.setFeeEndDate(null);
        }

        bookRentalFee.setDailyFee(new BigDecimal(dailyFee));
        bookRentalFeeRepository.save(bookRentalFee);
        return bookRentalFee;

    }
}
