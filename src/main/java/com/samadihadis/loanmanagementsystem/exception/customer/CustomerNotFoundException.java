package com.samadihadis.loanmanagementsystem.exception.customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
