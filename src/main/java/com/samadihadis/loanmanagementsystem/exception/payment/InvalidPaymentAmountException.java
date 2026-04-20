package com.samadihadis.loanmanagementsystem.exception.payment;

public class InvalidPaymentAmountException extends RuntimeException{
    public InvalidPaymentAmountException(String message){
        super(message);
    }
}
