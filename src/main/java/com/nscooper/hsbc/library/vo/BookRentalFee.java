package com.nscooper.hsbc.library.vo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@EnableAutoConfiguration
public class BookRentalFee {

    @Id
    private UUID id;

    @NotNull
    @OneToOne(targetEntity=Book.class, fetch= FetchType.EAGER)
    private Book book;

    @NotNull
    private ZonedDateTime feeStartDate;

    @Null
    private ZonedDateTime feeEndDate;

    @NotNull
    private BigDecimal dailyFee;

    @NotNull
    private BigDecimal dailyLateReturnFee;
}
