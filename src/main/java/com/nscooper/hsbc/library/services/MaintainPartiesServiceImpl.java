package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.repo.CustomerRepository;
import com.nscooper.hsbc.library.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MaintainPartiesServiceImpl implements MaintainPartiesService {

    @Autowired
    CustomerRepository customerRepository;

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
}
