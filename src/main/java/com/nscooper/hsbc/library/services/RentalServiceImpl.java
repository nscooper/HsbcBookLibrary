package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.repo.BookRentalFeeRepository;
import com.nscooper.hsbc.library.repo.BookRepository;
import com.nscooper.hsbc.library.repo.CustomerRepository;
import com.nscooper.hsbc.library.repo.RentalRepository;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import com.nscooper.hsbc.library.vo.Customer;
import com.nscooper.hsbc.library.vo.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class RentalServiceImpl implements RentalService{

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookRentalFeeRepository bookRentalFeeRepository;
    @Autowired
    RentalRepository rentalRepository;

    @Override
    public Customer addCustomer(String firstName, String lastName) throws LibraryException {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomer(String firstName, String lastName) throws LibraryException {
        Customer customer = customerRepository.findByFirstNameAndLastName(firstName, lastName);
        if (customer == null) {
            throw new LibraryException(String.format("No customer found with name:%s %s.", firstName, lastName));
        }
        return customer;
    }

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
            book.setTotalAvailableCopies(quantity);
        } else {
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setTotalStockedCopies(book.getTotalStockedCopies()+quantity);
            book.setTotalAvailableCopies(book.getTotalAvailableCopies()+quantity);
        }
        bookRepository.save(book);
        return book;

    }


    @Override
    @Transactional
    public Rental returnBook(UUID rentalAgreementReference) throws LibraryException {
        Rental rental = rentalRepository.findByRentalAgreementReference(rentalAgreementReference);
        if (rental==null){
            throw new LibraryException(String.format("No rental agreement found with reference:%s.", rentalAgreementReference));
        }
        Book book = rental.getBook();
        BookRentalFee bookRentalFee = getCurrentRentalFee(rental.getBook().getIsbn());
        if (bookRentalFee==null){
            throw new LibraryException(String.format("No current book fee available, for book with ISBN: :%s.",
                    book.getIsbn()));
        }

        @javax.validation.constraints.NotNull LocalDateTime actualReturnDate =  LocalDateTime.now();
        rental.setActualReturnedDate(actualReturnDate);
        long multiplier = Duration.between( rental.getRentalStartDate(), actualReturnDate ).toDays();
        rental.setTotalFee(bookRentalFee.getDailyFee().multiply(new BigDecimal(multiplier)).setScale(2, RoundingMode.UP));
        book.setTotalAvailableCopies(book.getTotalAvailableCopies()+1);
        bookRepository.save(book);
        rentalRepository.save(rental);
        return rental;
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
    public BookRentalFee getCurrentRentalFee(String isbn) throws LibraryException {
        Book book = bookRepository.findByIsbn(isbn);
        if (book==null){
            throw new LibraryException(String.format("No book found with ISBN:%s.", isbn));
        }
        return bookRentalFeeRepository.findByBook(book)
                .stream()
                .filter(fee -> fee.getFeeEndDate()==null)
                .findAny()
                .orElse(null);
    }

    @Override
    @Transactional
    public Rental rentBook(Customer customer, Book book, @javax.validation.constraints.NotNull LocalDateTime returnDate) throws LibraryException {

        BookRentalFee bookRentalFee = getCurrentRentalFee(book.getIsbn());
        if (bookRentalFee==null){
            throw new LibraryException(String.format("No current book fee available, for book with ISBN: :%s.",
                    book.getIsbn()));
        }

        Rental rental = new Rental();
        rental.setId(UUID.randomUUID());
        rental.setRentalAgreementReference(UUID.randomUUID());
        rental.setCustomer(customer);
        rental.setBook(book);
        rental.setRentalStartDate(LocalDateTime.now());
        rental.setAgreedReturnDate(returnDate);

        long multiplier = Duration.between( LocalDateTime.now() , returnDate ).toDays();
        rental.setTotalFee(bookRentalFee.getDailyFee().multiply(new BigDecimal(multiplier)));

        rentalRepository.save(rental);
        book.setTotalAvailableCopies(book.getTotalAvailableCopies()-1);
        bookRepository.save(book);
        return rental;
    }


    @Override
    public List<Rental> getRentals(Customer customer) throws LibraryException {
        return rentalRepository.findByCustomer(customer);
    }

    @Override
    @Transactional
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
            bookRentalFee.setFeeStartDate(LocalDateTime.now());
            bookRentalFee.setFeeEndDate(null);
        } else {
            bookRentalFee.setBook(book);
            bookRentalFee.setFeeEndDate(LocalDateTime.now());
            bookRentalFeeRepository.save(bookRentalFee);

            bookRentalFee = new BookRentalFee();
            bookRentalFee.setId(UUID.randomUUID());
            bookRentalFee.setBook(book);
            bookRentalFee.setFeeStartDate(LocalDateTime.now());
            bookRentalFee.setFeeEndDate(null);
        }

        bookRentalFee.setDailyFee(new BigDecimal(dailyFee));
        bookRentalFeeRepository.save(bookRentalFee);
        return bookRentalFee;

    }
}
