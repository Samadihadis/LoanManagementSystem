package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.dto.loan.CreateLoanRequest;
import com.samadihadis.loanmanagementsystem.dto.loan.LoanResponse;
import com.samadihadis.loanmanagementsystem.entity.Customer;
import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import com.samadihadis.loanmanagementsystem.exception.loan.InvalidLoanDataException;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanNotFoundException;
import com.samadihadis.loanmanagementsystem.repository.CustomerRepository;
import com.samadihadis.loanmanagementsystem.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    public final CustomerRepository customerRepository;

    public Loan createLoan(CreateLoanRequest request) {

        if (request.getPrincipalAmount() == null || request.getPrincipalAmount().signum() <= 0) {
            throw new InvalidLoanDataException("مبلغ وام باید مثبت و بیشتر از صفر باشد.");
        }

        if (request.getTerm() == null || request.getTerm() < 6 || request.getTerm() > 36) {
            throw new InvalidLoanDataException("مدت وام باید بین ۶ تا ۳۶ ماه باشد.");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new LoanNotFoundException(
                     String.format("مشتری با شناسه %d یافت نشد.", request.getCustomerId())
                ));

        Loan loan = new Loan();
        loan.setPrincipalAmount(request.getPrincipalAmount());
        loan.setInterestRate(request.getInterestRate());
        loan.setTerm(request.getTerm());
        loan.setStartDate(request.getStartDate());
        loan.setLoanType(request.getLoanType());
        loan.setCustomer(customer);

        loan.setLoanStatus(LoanStatus.PENDING);

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("مشتری با شناسه %d یافت نشد.", id)
                ));
    }

    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new LoanNotFoundException(
                    String.format("مشتری با شناسه %d یافت نشد.", id)
            );
        }
        loanRepository.deleteById(id);
    }

    public List<Loan> getLoanByCustomerId(Long customerId) {

        if (customerId == null) {
            throw new InvalidLoanDataException("شناسه مشتری نمی‌تواند خالی باشد");
        }

        customerRepository.findById(customerId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("مشتری با شناسه %d یافت نشد.", customerId)
                ));

        List<Loan> loans = loanRepository.findByCustomerId(customerId);

        if (loans.isEmpty()) {
            throw new LoanNotFoundException(
                    String.format("مشتری با شناسه %d یافت نشد.", customerId)
            );
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
            throw new InvalidLoanDataException("شناسه مشتری نمی‌تواند خالی باشد.");
        }
        return loanRepository.findByCustomerIdAndLoanStatus(customerId, loanStatus);
    }

    public List<Loan> getLoanByCustomerIdAndType(Long customerId, LoanType loanType) {

        if (customerId == null) {
            throw new InvalidLoanDataException("شناسه مشتری نمی‌تواند خالی باشد.");
        }
        return loanRepository.findByCustomerIdAndLoanType(customerId, loanType);
    }

    public LoanResponse toResponse(Loan loan) {

        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setPrincipalAmount(loan.getPrincipalAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTerm(loan.getTerm());
        response.setStartDate(loan.getStartDate());
        response.setMaturityDate(loan.getMaturityDate());
        response.setLoanStatus(loan.getLoanStatus());
        response.setLoanType(loan.getLoanType());

        if (loan.getCustomer() != null) {
            response.setCustomerId(loan.getCustomer().getId());
        }

        return response;
    }


}
