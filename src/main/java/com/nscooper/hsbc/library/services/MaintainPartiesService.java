package com.nscooper.hsbc.library.services;

import com.nscooper.hsbc.library.exceptions.LibraryException;
import com.nscooper.hsbc.library.vo.Customer;

public interface MaintainPartiesService {

    public Customer addCustomer(String firstName, String lastName)  throws LibraryException;

    Customer getCustomer(String firstName, String lastName)  throws LibraryException;
}
