package com.nscooper.hsbc.library.controllers;

import com.nscooper.hsbc.library.controllers.dto.BookDto;
import com.nscooper.hsbc.library.controllers.dto.BookRentalFeeDto;
import com.nscooper.hsbc.library.controllers.dto.CustomerDto;
import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.services.MaintainPartiesService;
import com.nscooper.hsbc.library.services.RentalService;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import com.nscooper.hsbc.library.vo.Customer;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;
    @Autowired
    private MaintainPartiesService maintainPartiesService;

    @ApiOperation("POST add a book to the library, including defining how many copies." +
            "If the book already exists, the number of copies will be increased.")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/addBook")
    public Book addBook(
            @RequestBody BookDto bookDetails,
            final HttpServletRequest req) throws LibraryException {

        logger.info("Started /addBook");
        logger.info(bookDetails.getIsbn());

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

    @ApiOperation("Add a customer to the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/addCustomer")
    public Customer addCustomer(
            @RequestBody CustomerDto customerDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addCustomer");

        return maintainPartiesService.addCustomer(customerDto.getFirstName(),
                customerDto.getLastName());
    }

    @ApiOperation("Get a customer from the library")
    @PostMapping(consumes= APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE,
            path = "/getCustomer")
    public Customer getCustomer(
            @RequestBody CustomerDto customerDto,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /getCustomer");

        return maintainPartiesService.getCustomer(customerDto.getFirstName(),
                customerDto.getLastName());
    }
}
