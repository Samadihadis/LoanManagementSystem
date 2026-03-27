package com.samadihadis.loanmanagementsystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate paymentDate;      //تاریخی که پول واقعا دریافت شده است

    @NotNull(message = "مبلغ تراکنش نمیتواند خالی باشد.")
    @Positive(message = "مبلغ تراکنش باید مثبت باشد.")
    private BigDecimal amountPaid;      //کل مبلغی که در این تراکنش پرداخت شده

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
}
