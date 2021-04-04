package com.nscooper.hsbc.library.vo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@EnableAutoConfiguration
public class Rental {

    @Id
    private UUID id;

    @NotNull
    @OneToOne(targetEntity=Customer.class, fetch= FetchType.EAGER)
    Customer customer;

    @NotNull
    @OneToOne(targetEntity=Book.class, fetch= FetchType.EAGER)
    Book book;

    @NotNull
    ZonedDateTime rentalStartDate;

    @NotNull
    ZonedDateTime agreedReturnDate;

    @NotNull
    ZonedDateTime actualReturnedDate;
}
