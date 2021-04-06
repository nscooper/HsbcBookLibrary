package com.nscooper.hsbc.library.controllers.dto;

public class RentalDto {

    private String customerFirstName;
    private String customerLastName;

    private String isbn;
    private String bookTitle;
    private String bookAuthor;

    private String numberOfDaysRental;
    private String rentalAgreementReference;


    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getNumberOfDaysRental() {
        return numberOfDaysRental;
    }

    public void setNumberOfDaysRental(String numberOfDaysRental) {
        this.numberOfDaysRental = numberOfDaysRental;
    }

    public String getRentalAgreementReference() {
        return rentalAgreementReference;
    }

    public void setRentalAgreementReference(String rentalAgreementReference) {
        this.rentalAgreementReference = rentalAgreementReference;
    }
}
