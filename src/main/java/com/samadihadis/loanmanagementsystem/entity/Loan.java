package com.samadihadis.loanmanagementsystem.entity;


import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal principalAmount;   //مقدار کل مبلف وامی که دریافت شده - مبلغ اصلی وام

    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate;   //نرخ بهره سالانه

    @Min(value = 6, message = "مدت وام حداقل 6 ماه است")
    @Max(value = 36, message = "مدت وام حداکثر 36 ماه است")
    private Integer term;    // تعداد ماه های بازپرداخت

    @NotNull(message = "تاریخ شروع وام الزامی است")
    @Column(nullable = false)
    private LocalDate startDate;      //تاریخی که وام شروع شده است

    @Future(message = "تاریخ سررسید باید در آینده باشد")
    @Column(nullable = false)
    private LocalDate maturityDate;

    private LoanStatus loanStatus;

    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
