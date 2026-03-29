package com.samadihadis.loanmanagementsystem.controller;

import com.samadihadis.loanmanagementsystem.entity.Payments;
import com.samadihadis.loanmanagementsystem.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @PostMapping("/loan/{loanId}")
    public ResponseEntity<Payments> createPayment(@RequestBody @Validated Payments payments, @PathVariable Long loanId) {
        try {
            var createPayment = paymentsService.createPayment(loanId, payments);
            return ResponseEntity.ok(createPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/loan/{loanId}/first")
    public ResponseEntity<Payments> getFirstPaymentByLoanId(@PathVariable Long loanId){
        return ResponseEntity.ok(paymentsService.getFirstPaymentByLoanId(loanId));
    }

    @GetMapping("/loan/{loanId}/last")
    public ResponseEntity<Payments> getLastPaymentByLoanId(@PathVariable Long loanId){
        return ResponseEntity.ok(paymentsService.getLastPaymentByLoanId(loanId));
    }

    @GetMapping("/loan/{loanId}/is-paid")
    public ResponseEntity<?> isLoanFullyPaid(@PathVariable Long loanId) {

        boolean isPaid = paymentsService.isLoanFullyPaid(loanId);
        if (isPaid) {
            return ResponseEntity.ok("وام تسویه شده است");
        } else {
            return ResponseEntity.ok("وام هنوز تسویه نشده است");
        }
    }

    @GetMapping("/loan/{loanId}/remaining")
    public ResponseEntity<BigDecimal> getRemainingBalance(@PathVariable Long loanId) {

        BigDecimal remaining = paymentsService.getRemainingBalance(loanId);
        return ResponseEntity.ok(remaining);
    }

}
