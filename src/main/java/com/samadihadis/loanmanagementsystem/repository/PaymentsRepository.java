package com.samadihadis.loanmanagementsystem.repository;

import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    Payments findTopByLoanOrderByPaymentDateAsc(Loan loan);    //پیدا کردن اولین پرداخت

    Payments findTopByLoanOrderByPaymentDateDesc(Loan loan);   //پیدا کردن آخرین پرداخت

    List<Payments> findByLoan(Loan loan);  // پیدا کردن تمام پرداخت های وام
}
