package com.nscooper.hsbc.library.repo;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.BookRentalFee;

public interface BookRentalFeeRepository {

    public BookRentalFee addBookRentalFee(BookRentalFee bookRentalFee) throws LibraryException;
    public BookRentalFee updateBookRentalFee(BookRentalFee bookRentalFee) throws LibraryException;
    public BookRentalFee getBookRentalFee(String isbn) throws LibraryException;
}
