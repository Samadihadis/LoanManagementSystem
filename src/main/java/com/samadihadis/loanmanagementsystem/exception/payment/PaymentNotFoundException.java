package com.samadihadis.loanmanagementsystem.exception.payment;

import com.samadihadis.loanmanagementsystem.entity.Payments;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException (String message) {
        super(message);
    }
}
