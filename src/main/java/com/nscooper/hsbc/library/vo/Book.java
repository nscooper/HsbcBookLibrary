package com.nscooper.hsbc.library.vo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@EnableAutoConfiguration
public class Book {

    /**
     * A publication fit for rental to customers
     */
    @Id private UUID id;
    @NotNull private String isbn;
    @NotNull private String title;
    @NotNull private String author;
    @NotNull private int totalStockedCopies;
}
