package com.samadihadis.loanmanagementsystem.entity;


import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer installmentNumber;     //شماره قسط مثلا ( 1 از 36)

    private LocalDate dueDate;   //تاریخی که قسط باید پرداخت شود

    private BigDecimal totalInstallmentAmount;    //مبلغ کل هر قسط

    private RepaymentScheduleStatus repaymentScheduleStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;         //کلید خارجی (foreign key) برای اتصال به وام مربوطه

}
