package com.nscooper.hsbc.library.repo;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Customer;
import com.nscooper.hsbc.library.vo.Rental;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends CrudRepository<Rental, UUID> {

    public Rental findByRentalAgreementReference(UUID rentalAgreementReference) throws LibraryException;
    public List<Rental> findByCustomer(Customer customer) throws LibraryException;

}
