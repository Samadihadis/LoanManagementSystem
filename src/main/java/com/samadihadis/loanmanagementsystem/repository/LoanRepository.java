package com.samadihadis.loanmanagementsystem.repository;

import com.samadihadis.loanmanagementsystem.entity.Customer;
import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomer(Customer customer);

    List<Loan> findByLoanStatus(LoanStatus loanStatus);

    List<Loan> findByLoanType(LoanType loanType);

    List<Loan> findByCustomerAndLoanStatus(Customer customer, LoanStatus loanStatus);

    List<Loan> findByCustomerAndLoanType(Customer customer, LoanType loanType);
}
