package com.samadihadis.loanmanagementsystem.exception.loan;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
