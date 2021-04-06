package com.nscooper.hsbc.library.repo;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BookRentalFeeRepository extends CrudRepository<BookRentalFee, UUID> {

    public List<BookRentalFee> findByBook(Book book) throws LibraryException;
}
