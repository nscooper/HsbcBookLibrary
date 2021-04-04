package com.nscooper.hsbc.library.vo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

@Entity
@EnableAutoConfiguration
public class Customer {

    @Id
    private UUID id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


}
