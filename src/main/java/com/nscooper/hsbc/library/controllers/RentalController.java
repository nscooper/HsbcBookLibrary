package com.nscooper.hsbc.library.controllers;

import com.nscooper.hsbc.library.controllers.dto.BookDto;
import com.nscooper.hsbc.library.controllers.dto.BookRentalFeeDto;
import com.nscooper.hsbc.library.controllers.dto.CustomerDto;
import com.nscooper.hsbc.library.controllers.dto.RentalDto;
import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.services.RentalService;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import com.nscooper.hsbc.library.vo.Customer;
import com.nscooper.hsbc.library.vo.Rental;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @ApiOperation("POST add a book to the library, including defining how many copies." +
            "If the book already exists, the number of copies will be increased.")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/addBook")
    public Book addBook(
            @RequestBody BookDto bookDetails,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addBook");

        return rentalService.addBook(bookDetails.getIsbn(), bookDetails.getTitle(), bookDetails.getAuthor(), bookDetails.getQuantity());
    }

    @ApiOperation("POST get a book from the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/getBook")
    public Book getBook(
            @RequestBody BookDto bookDetails,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /getBook");

        return rentalService.getBook(bookDetails.getIsbn());
    }

    @ApiOperation("Add a rental fee to a book")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/addRentalFee")
    public BookRentalFee addRentalFee(
            @RequestBody BookRentalFeeDto bookRentalFeeDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addRentalFee");

        return rentalService.addRentalFee(bookRentalFeeDto.getIsbn(),
                bookRentalFeeDto.getDailyFee().toString());
    }

    @ApiOperation("POST get a book's rental fee from the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/getRentalFee")
    public List<BookRentalFee> getRentalFee(
            @RequestBody BookRentalFeeDto bookRentalFeeDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /getRentalFee");

        return rentalService.getRentalFee(bookRentalFeeDto.getIsbn());
    }

    @ApiOperation("POST get a book's rental fee from the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/getCurrentRentalFee")
    public BookRentalFee getCurrentRentalFee(
            @RequestBody BookRentalFeeDto bookRentalFeeDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /getCurrentRentalFee");

        return rentalService.getCurrentRentalFee(bookRentalFeeDto.getIsbn());
    }

    @ApiOperation("Add a customer to the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/addCustomer")
    public Customer addCustomer(
            @RequestBody CustomerDto customerDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addCustomer");

        return rentalService.addCustomer(customerDto.getFirstName(),
                customerDto.getLastName());
    }

    @ApiOperation("Get a customer from the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/getCustomer")
    public Customer getCustomer(
            @RequestBody CustomerDto customerDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /getCustomer");

        return rentalService.getCustomer(customerDto.getFirstName(),
                customerDto.getLastName());
    }

    @ApiOperation("add a rental transaction")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE,
            path = "/addRental")
    public Rental addRental(
            @RequestBody RentalDto rentalDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addRental");

        Customer customer =
                rentalService.getCustomer(rentalDto.getCustomerFirstName(), rentalDto.getCustomerLastName());

        Book book = rentalService.getBook(rentalDto.getIsbn());
        if (book != null && book.getTotalAvailableCopies()<1) {
            throw new LibraryException(String.format("Book '%s' by %s is unavailable",book.getTitle(), book.getAuthor()));
        }

        @NotNull LocalDateTime endRentalDate = LocalDateTime
                .now()
                .plusDays( Long.parseLong(rentalDto.getNumberOfDaysRental()) );
        return rentalService.rentBook(customer, book, endRentalDate);
    }

    @ApiOperation("conclude a rental transaction by returning the book")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE,
            path = "/endRental")
    public Rental endRental(
            @RequestBody RentalDto rentalDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /endRental");
        return rentalService.returnBook(UUID.fromString(rentalDto.getRentalAgreementReference()));
    }

    @ApiOperation("get customers rentals")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/getRentals")
    public List<Rental> getRental(
            @RequestBody RentalDto rentalDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /getRentals");

       return rentalService.getRentals(
               rentalService.getCustomer(rentalDto.getCustomerFirstName(), rentalDto.getCustomerLastName()));

    }
}
