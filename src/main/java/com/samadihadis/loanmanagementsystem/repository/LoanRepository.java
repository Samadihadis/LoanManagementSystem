package com.samadihadis.loanmanagementsystem.repository;

import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomerId(Long id);

    List<Loan> findByLoanStatus(LoanStatus loanStatus);

    List<Loan> findByLoanType(LoanType loanType);

    List<Loan> findByCustomerIdAndLoanStatus(Long id, LoanStatus loanStatus);

    List<Loan> findByCustomerIdAndLoanType(Long id, LoanType loanType);
}
