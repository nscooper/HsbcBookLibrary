package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.repo.BookRentalFeeRepository;
import com.nscooper.hsbc.library.repo.BookRepository;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class RentalServiceImpl implements RentalService{

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookRentalFeeRepository bookRentalFeeRepository;

    @Override
    public Book addBook(String isbn, String title, String author, int quantity) throws LibraryException {

        Book book = bookRepository.getBook(isbn);
        if (book==null) {
            book = new Book();
            book.setId(UUID.randomUUID());
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setTotalStockedCopies(quantity);
            return bookRepository.addBook(book);
        } else {
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setTotalStockedCopies(book.getTotalStockedCopies()+quantity);
            return bookRepository.updateBook(book);
        }

    }


    @Override
    public void rentBook() throws LibraryException {

    }

    @Override
    public void returnBook() throws LibraryException {

    }

    @Override
    public void calculateLateReturnPenalty() throws LibraryException {

    }

    @Override
    public BookRentalFee addRentalFee(String isbn, String dailyFee, String startDate) throws LibraryException {

        Book book = bookRepository.getBook(isbn);
        if (book==null){
            throw new LibraryException(String.format("No book found with ISBN:%s so cannot add a book fee.", isbn));
        }

        BookRentalFee bookRentalFee = bookRentalFeeRepository.getBookRentalFee(isbn);


        bookRentalFee.setDailyFee(new BigDecimal(dailyFee));
        if (bookRentalFee==null) {
            bookRentalFee = new BookRentalFee();
            bookRentalFee.setId(UUID.randomUUID());
            bookRentalFee.setBook(book);
            bookRentalFee.setFeeStartDate(ZonedDateTime.now());
            bookRentalFee.setFeeEndDate(null);
            return bookRentalFeeRepository.addBookRentalFee(bookRentalFee);
        } else {

            bookRentalFee.setBook(book);
            bookRentalFee.setFeeStartDate(ZonedDateTime.now());
            bookRentalFee.setFeeEndDate(null);
            return bookRentalFeeRepository.updateBookRentalFee(bookRentalFee);
        }

    }
}
