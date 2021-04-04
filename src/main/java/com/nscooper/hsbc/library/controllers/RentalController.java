package com.nscooper.hsbc.library.controllers;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.services.RentalService;
import com.nscooper.hsbc.library.vo.Rental;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.json.JSONObject;

@RestController
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    private static final String RESULT_SIZE = "Number of price records found";
    private static final String ERROR = "Error trying to obtain instrument prices for ISIN";
    private JSONObject jsonOutcome = null;
    private String jsonString = null;

    @RequestMapping(value = "/addBook", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody
    String addBook(
            @RequestParam(value = "isbn", defaultValue = "None") final String isbn,
            @RequestParam(value = "title", defaultValue = "None") final String title,
            @RequestParam(value = "author", defaultValue = "None") final String author,
            @RequestParam(value = "quantity", defaultValue = "0") final int quantity,
            final HttpServletRequest req) {

        jsonOutcome = new JSONObject();
        try {
            jsonOutcome = rentalService.addBook(isbn, title, author, quantity);
        } catch (LibraryException e) {
            logger.error(e.getLocalizedMessage());
            jsonOutcome.put(ERROR, e.getLocalizedMessage());
        }
        return jsonOutcome.toString(20);
    }


    @RequestMapping(value = "/addBook", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody
    String addRentalFee(
            @RequestParam(value = "isbn", defaultValue = "None") final String isbn,
            @RequestParam(value = "fee", defaultValue = "None") final String title,
            @RequestParam(value = "author", defaultValue = "None") final String author,
            @RequestParam(value = "quantity", defaultValue = "0") final int quantity,
            final HttpServletRequest req) {

        jsonOutcome = new JSONObject();
        try {
            jsonOutcome = rentalService.addBook(isbn, title, author, quantity);
        } catch (LibraryException e) {
            logger.error(e.getLocalizedMessage());
            jsonOutcome.put(ERROR, e.getLocalizedMessage());
        }
        return jsonOutcome.toString(20);
    }


}
