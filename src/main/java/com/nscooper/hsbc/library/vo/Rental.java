package com.nscooper.hsbc.library.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.nscooper.hsbc.library.config.Configuration.DATETIME_PATTERN;

@Entity
@EnableAutoConfiguration
@Table(name = "RENTAL")
public class Rental {

    @Id
    @Column(name="ID")
    private UUID id;

    @NotNull
    @Column(name="RENTAL_AGREEMENT_REFERENCE")
    private UUID rentalAgreementReference;

    @NotNull
    @ManyToOne(targetEntity=Customer.class, fetch= FetchType.EAGER)
    Customer customer;

    @NotNull
    @ManyToOne(targetEntity=Book.class, fetch= FetchType.EAGER)
    Book book;

    @Column(name="RENTAL_START_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    @NotNull
    LocalDateTime rentalStartDate;

    @Column(name="AGREED_RETURN_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    @NotNull
    LocalDateTime agreedReturnDate;

    @Column(name="ACTUAL_RETURN_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    @NotNull
    LocalDateTime actualReturnedDate;

    @Null
    @Column(name="TOTAL_FEE")
    BigDecimal totalFee;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRentalAgreementReference() {
        return rentalAgreementReference;
    }

    public void setRentalAgreementReference(UUID rentalAgreementReference) {
        this.rentalAgreementReference = rentalAgreementReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public @NotNull LocalDateTime getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(@NotNull LocalDateTime rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public @NotNull LocalDateTime getAgreedReturnDate() {
        return agreedReturnDate;
    }

    public void setAgreedReturnDate(@NotNull LocalDateTime agreedReturnDate) {
        this.agreedReturnDate = agreedReturnDate;
    }

    public @NotNull LocalDateTime getActualReturnedDate() {
        return actualReturnedDate;
    }

    public void setActualReturnedDate(@NotNull LocalDateTime actualReturnedDate) {
        this.actualReturnedDate = actualReturnedDate;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }
}
