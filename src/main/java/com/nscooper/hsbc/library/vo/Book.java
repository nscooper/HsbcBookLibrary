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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalStockedCopies() {
        return totalStockedCopies;
    }

    public void setTotalStockedCopies(int totalStockedCopies) {
        this.totalStockedCopies = totalStockedCopies;
    }
}
