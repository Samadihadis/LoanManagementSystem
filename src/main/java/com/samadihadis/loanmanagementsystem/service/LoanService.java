package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import com.samadihadis.loanmanagementsystem.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.orElse(null);
    }

    public void deleteLoan(long id) {
        loanRepository.deleteById(id);
    }

    public List<Loan> getLoanByCustomerId(Long customerId) {

        if (customerId == null) {
            throw new IllegalArgumentException("Customer and customer ID must not be null");
        }
        List<Loan> loans = loanRepository.findByCustomerId(customerId);

        if (loans.isEmpty()) {
            throw new IllegalArgumentException("برای مشتری با شناسه " + customerId + " وامی یافت نشد.");
        }

        return loans;
    }

    public List<Loan> getLoanByStatus(LoanStatus loanStatus) {
        return loanRepository.findByLoanStatus(loanStatus);
    }

    public List<Loan> getLoanByType(LoanType loanType) {
        return loanRepository.findByLoanType(loanType);
    }

    public List<Loan> getLoanByCustomerIdAndStatus(Long customerId, LoanStatus loanStatus) {

        if (customerId == null) {
            throw new IllegalArgumentException("Customer and customer ID must not be null");
        }

        List<Loan> loans = loanRepository.findByCustomerIdAndLoanStatus(customerId, loanStatus);

        if (loans.isEmpty()) {
            throw new IllegalArgumentException(String.format("برای مشتری با شناسه %d و همچنین وضعیت %s وامی یافت نشد" ,
                    customerId , loanStatus));
        }

        return loans;
    }

    public List<Loan> getLoanByCustomerIdAndType(Long customerId, LoanType loanType) {

        if (customerId == null) {
            throw new IllegalArgumentException("Customer and customer ID must not be null");
        }

        List<Loan> loans = loanRepository.findByCustomerIdAndLoanType(customerId, loanType);

        if (loans.isEmpty()) {
            throw new IllegalArgumentException(String.format("برای مشتری با شناسه %d و همچنین نوع %s وامی یافت نشد" ,
                    customerId , loanType));
        }

        return loans;
    }

}
