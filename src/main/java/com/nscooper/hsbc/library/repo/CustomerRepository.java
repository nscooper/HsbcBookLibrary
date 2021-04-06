package com.nscooper.hsbc.library.repo;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    public Customer findByFirstNameAndLastName(String firstName, String lastName) throws LibraryException;
}
