package com.samadihadis.loanmanagementsystem.controller;

import com.samadihadis.loanmanagementsystem.dto.payments.CreatePaymentRequest;
import com.samadihadis.loanmanagementsystem.dto.payments.PaymentResponse;
import com.samadihadis.loanmanagementsystem.entity.Payments;
import com.samadihadis.loanmanagementsystem.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments/loan")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @PostMapping("/{loanId}")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Validated CreatePaymentRequest request
            , @PathVariable Long loanId) {
        try {
            Payments payment = new Payments();
            payment.setAmountPaid(request.getAmountPaid());
            payment.setPaymentDate(request.getPaymentDate());

            Payments savedPayment = paymentsService.createPayment(loanId, payment);
            return ResponseEntity.ok(paymentsService.toResponse(savedPayment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{loanId}/first")
    public ResponseEntity<Payments> getFirstPaymentByLoanId(@PathVariable Long loanId){
        return ResponseEntity.ok(paymentsService.getFirstPaymentByLoanId(loanId));
    }

    @GetMapping("/{loanId}/last")
    public ResponseEntity<Payments> getLastPaymentByLoanId(@PathVariable Long loanId){
        return ResponseEntity.ok(paymentsService.getLastPaymentByLoanId(loanId));
    }

    @GetMapping("/{loanId}/is-paid")
    public ResponseEntity<?> isLoanFullyPaid(@PathVariable Long loanId) {

        boolean isPaid = paymentsService.isLoanFullyPaid(loanId);
        if (isPaid) {
            return ResponseEntity.ok("وام تسویه شده است");
        } else {
            return ResponseEntity.ok("وام هنوز تسویه نشده است");
        }
    }

    @GetMapping("/{loanId}/remaining")
    public ResponseEntity<BigDecimal> getRemainingBalance(@PathVariable Long loanId) {

        BigDecimal remaining = paymentsService.getRemainingBalance(loanId);
        return ResponseEntity.ok(remaining);
    }

}
