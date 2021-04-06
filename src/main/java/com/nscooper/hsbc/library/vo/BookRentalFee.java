package com.nscooper.hsbc.library.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import static com.nscooper.hsbc.library.config.Configuration.DATETIME_PATTERN;

@Entity
@EnableAutoConfiguration
@Table(name = "BOOK_RENTAL_FEE")
public class BookRentalFee {

    @Id
    @Column(name="ID")
    private UUID id;

    @NotNull
    @ManyToOne(targetEntity=Book.class, fetch= FetchType.EAGER)
    private Book book;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private ZonedDateTime feeStartDate;

    @Null
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private ZonedDateTime feeEndDate;

    @NotNull
    private BigDecimal dailyFee;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ZonedDateTime getFeeStartDate() {
        return feeStartDate;
    }

    public void setFeeStartDate(ZonedDateTime feeStartDate) {
        this.feeStartDate = feeStartDate;
    }

    public ZonedDateTime getFeeEndDate() {
        return feeEndDate;
    }

    public void setFeeEndDate(ZonedDateTime feeEndDate) {
        this.feeEndDate = feeEndDate;
    }

    public BigDecimal getDailyFee() {
        return dailyFee;
    }

    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }
}
