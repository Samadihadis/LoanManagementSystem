package com.samadihadis.loanmanagementsystem.exception.customer;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
