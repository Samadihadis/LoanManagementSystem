package com.samadihadis.loanmanagementsystem.dto.payments;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class PaymentResponse {

    private Long id;
    private LocalDate paymentDate;
    private BigDecimal amountPaid;
    private Long loanId;
}
