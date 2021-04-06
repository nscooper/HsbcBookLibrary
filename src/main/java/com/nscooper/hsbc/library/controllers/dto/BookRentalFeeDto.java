package com.nscooper.hsbc.library.controllers.dto;

public class BookRentalFeeDto {

    private String isbn;

    private String dailyFee;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDailyFee() {
        return dailyFee;
    }

    public void setDailyFee(String dailyFee) {
        this.dailyFee = dailyFee;
    }
}
