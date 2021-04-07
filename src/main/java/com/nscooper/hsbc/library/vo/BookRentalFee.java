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
@Table(name = "BOOK_RENTAL_FEE")
public class BookRentalFee {

    @Id
    @Column(name="ID")
    private UUID id;

    @NotNull
    @ManyToOne(targetEntity=Book.class, fetch= FetchType.EAGER)
    private Book book;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private @NotNull LocalDateTime feeStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private @Null LocalDateTime feeEndDate;

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

    public @NotNull LocalDateTime getFeeStartDate() {
        return feeStartDate;
    }

    public void setFeeStartDate(@NotNull LocalDateTime feeStartDate) {
        this.feeStartDate = feeStartDate;
    }

    public @Null LocalDateTime getFeeEndDate() {
        return feeEndDate;
    }

    public void setFeeEndDate(@Null LocalDateTime feeEndDate) {
        this.feeEndDate = feeEndDate;
    }

    public BigDecimal getDailyFee() {
        return dailyFee;
    }

    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }
}
