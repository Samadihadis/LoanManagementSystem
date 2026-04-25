package com.samadihadis.loanmanagementsystem.exception.repaymentSchedule;

public class InvalidInstallmentAmountException extends RuntimeException {
    public InvalidInstallmentAmountException(String message) {
        super(message);
    }
}