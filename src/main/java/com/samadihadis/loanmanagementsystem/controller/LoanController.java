package com.samadihadis.loanmanagementsystem.controller;

import com.samadihadis.loanmanagementsystem.dto.loan.CreateLoanRequest;
import com.samadihadis.loanmanagementsystem.dto.loan.LoanResponse;
import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import com.samadihadis.loanmanagementsystem.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@RequestBody @Valid CreateLoanRequest request) {
        Loan savedLoan = loanService.createLoan(request);
        return ResponseEntity.ok(loanService.toResponse(savedLoan));
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.ok(
                String.format("وام با شناسه %d با موفقیت حذف شد.", id));
    }

    @GetMapping("/customer-id/{id}")
    public ResponseEntity<?> getLoanByCustomerId(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanByCustomerId(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.getLoanByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanType type) {
        return ResponseEntity.ok(loanService.getLoanByType(type));
    }

    @GetMapping("/customer-id-status/{id}/{status}")
    public ResponseEntity<?> getLoanByCustomerIdAndStatus(@PathVariable Long id, @PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.getLoanByCustomerIdAndStatus(id, status));
    }

    @GetMapping("/customer-id-type/{id}/{type}")
    public ResponseEntity<?> getLoanByCustomerIdAndType(@PathVariable Long id, @PathVariable LoanType type) {
        return ResponseEntity.ok(loanService.getLoanByCustomerIdAndType(id, type));
    }

}
