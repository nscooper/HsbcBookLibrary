package com.nscooper.hsbc.library.controllers;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.services.RentalService;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @GetMapping(consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE, path = "/addBook")
    public Book addBook(
            @RequestParam(value = "isbn", defaultValue = "None") final String isbn,
            @RequestParam(value = "title", defaultValue = "None") final String title,
            @RequestParam(value = "author", defaultValue = "None") final String author,
            @RequestParam(value = "quantity", defaultValue = "0") final int quantity,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addBook");

        return rentalService.addBook(isbn, title, author, quantity);
    }



    @GetMapping(consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE, path = "/addRentalFee")
    public BookRentalFee addRentalFee(
            @RequestParam(value = "isbn", defaultValue = "None") final String isbn,
            @RequestParam(value = "dailyFee", defaultValue = "None") final String dailyFee,
            @RequestParam(value = "startDate", defaultValue = "None") final String startDate,
            final HttpServletRequest req) throws LibraryException {
        logger.debug("Started /addRentalFee");

        return rentalService.addRentalFee(isbn, dailyFee, startDate);
    }



}
