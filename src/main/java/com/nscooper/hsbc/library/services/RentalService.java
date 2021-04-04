package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import org.json.JSONObject;

public interface RentalService {

    public JSONObject addBook(String isbn, String title, String author, int quantity) throws LibraryException;
    public void addRentalFee() throws LibraryException;
    public void supersedeRentalFee() throws LibraryException;

    public void rentBook() throws LibraryException;
    public void returnBook() throws LibraryException;
    public void calculateLateReturnPenalty() throws LibraryException;
}
