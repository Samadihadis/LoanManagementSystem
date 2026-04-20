package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.dto.payments.PaymentResponse;
import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.entity.Payments;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanNotFoundException;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanStatusException;
import com.samadihadis.loanmanagementsystem.exception.payment.InvalidPaymentAmountException;
import com.samadihadis.loanmanagementsystem.exception.payment.PaymentNotFoundException;
import com.samadihadis.loanmanagementsystem.repository.LoanRepository;
import com.samadihadis.loanmanagementsystem.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;
    private final LoanRepository loanRepository;


    public Payments createPayment(Long loanId, Payments payment) {

        if (payment.getAmountPaid() == null || payment.getAmountPaid().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentAmountException("مبلغ پرداختی باید مثبت باشد.");
        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)
                ));

        if (loan.getLoanStatus() == LoanStatus.PAID) {
            throw new LoanStatusException("وام قبلا تسویه شده و نمیتوان پرداختی ثبت کرد.");
        }

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }

        payment.setLoan(loan);

        Payments savePayment = paymentsRepository.save(payment);

        updateLoanStatusAfterPayment(loan);

        return savePayment;
    }

    private void updateLoanStatusAfterPayment(Loan loan) {
        BigDecimal totalPaid = getTotalPaidAmountByLoanId(loan.getId());

        if (totalPaid.compareTo(loan.getPrincipalAmount()) >= 0) {
            loan.setLoanStatus(LoanStatus.PAID);
            loanRepository.save(loan);
        } else if (totalPaid.compareTo(BigDecimal.ZERO) > 0) {
            loan.setLoanStatus(LoanStatus.ACTIVE);
            loanRepository.save(loan);
        }
    }


    public BigDecimal getTotalPaidAmountByLoanId(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        List<Payments> payments = paymentsRepository.findByLoan(loan);

        BigDecimal totalPaid = payments.stream()
                .map(Payments::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPaid;
    }

    public Payments getFirstPaymentByLoanId(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        Payments firstPayment = paymentsRepository.findTopByLoanOrderByPaymentDateAsc(loan);

        if (firstPayment == null) {
            throw new PaymentNotFoundException("هیچ پرداختی برای این وام یافت نشد.");
        }

        return firstPayment;
    }

    public Payments getLastPaymentByLoanId(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        Payments lastPayment = paymentsRepository.findTopByLoanOrderByPaymentDateDesc(loan);

        if (lastPayment == null) {
            throw new PaymentNotFoundException("هیچ پرداختی برای این وام یافت نشد.");
        }

        return lastPayment;
    }

    public boolean isLoanFullyPaid(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        BigDecimal totalPaid = getTotalPaidAmountByLoanId(loanId);
        return totalPaid.compareTo(loan.getPrincipalAmount()) >= 0;
    }

    public BigDecimal getRemainingBalance(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        BigDecimal totalPaid = getTotalPaidAmountByLoanId(loanId);
        BigDecimal remaining = loan.getPrincipalAmount().subtract(totalPaid);

        return remaining.compareTo(BigDecimal.ZERO) > 0 ? remaining : BigDecimal.ZERO;
    }

    public PaymentResponse toResponse(Payments payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setPaymentDate(payment.getPaymentDate());
        response.setAmountPaid(payment.getAmountPaid());
        if (payment.getLoan() != null) {
            response.setLoanId(payment.getLoan().getId());
        }
        return response;
    }

}
