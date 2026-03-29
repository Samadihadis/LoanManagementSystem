package com.samadihadis.loanmanagementsystem.controller;

import com.samadihadis.loanmanagementsystem.entity.Customer;
import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import com.samadihadis.loanmanagementsystem.service.CustomerService;
import com.samadihadis.loanmanagementsystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody @Validated Loan loan) {
        try {
            var createLoan = loanService.createLoan(loan);
            return ResponseEntity.ok(createLoan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);

        if (loan != null) {
            return ResponseEntity.ok(loan);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        String.format("وام با شناسه %d یافت نشد.", id)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long id) {

        Loan loan = loanService.getLoanById(id);

        if (loan != null) {
            loanService.deleteLoan(id);
            return ResponseEntity.ok()
                    .body(String.format("وام با شناسه %d حذف شد.", id));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        String.format("وام با شناسه %d یافت نشد.", id)
                );
    }

    @GetMapping("/customer-id/{id}")
    public ResponseEntity<?> getLoanByCustomerId(@PathVariable Long id) {
        List<Loan> loans = loanService.getLoanByCustomerId(id);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanStatus status) {
        List<Loan> loans = loanService.getLoanByStatus(status);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanType type) {
        List<Loan> loans = loanService.getLoanByType(type);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/customer-id-status/{id}/{status}")
    public ResponseEntity<?> getLoanByCustomerIdAndStatus(@PathVariable Long id, @PathVariable LoanStatus status) {
        List<Loan> loans = loanService.getLoanByCustomerIdAndStatus(id , status);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/customer-id-type/{id}/{type}")
    public ResponseEntity<?> getLoanByCustomerIdAndType(@PathVariable Long id, @PathVariable LoanType type) {
        List<Loan> loans = loanService.getLoanByCustomerIdAndType(id , type);
        return ResponseEntity.ok(loans);
    }

}
