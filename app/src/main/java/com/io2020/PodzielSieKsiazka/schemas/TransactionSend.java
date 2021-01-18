package com.io2020.PodzielSieKsiazka.schemas;

public class TransactionSend {

    private int bookId;
    private int daysOfRentalTime;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getDaysOfRentalTime() {
        return daysOfRentalTime;
    }

    public void setDaysOfRentalTime(int daysOfRentalTime) {
        this.daysOfRentalTime = daysOfRentalTime;
    }
}
