package com.samadihadis.loanmanagementsystem.dto.payments;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class CreatePaymentRequest {

    private LocalDate paymentDate;

    @NotNull(message = "مبلغ تراکنش نمیتواند خالی باشد.")
    @Positive(message = "مبلغ تراکنش باید مثبت باشد.")
    private BigDecimal amountPaid;
}
