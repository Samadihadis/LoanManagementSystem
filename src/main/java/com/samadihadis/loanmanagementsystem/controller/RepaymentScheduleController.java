package com.samadihadis.loanmanagementsystem.controller;


import com.samadihadis.loanmanagementsystem.entity.RepaymentSchedule;
import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import com.samadihadis.loanmanagementsystem.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/repayment-schedule")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;


    @PostMapping("/{loanId}")
    public ResponseEntity<RepaymentSchedule> createRepaymentSchedule(@RequestBody @Validated RepaymentSchedule repaymentSchedule
            , @PathVariable Long loanId) {

        try {
            var createRepaymentSchedule = repaymentScheduleService.createRepaymentSchedule(loanId, repaymentSchedule);
            return ResponseEntity.ok(createRepaymentSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRepaymentScheduleById(@PathVariable Long id) {
        RepaymentSchedule repaymentSchedule = repaymentScheduleService.getRepaymentScheduleById(id);

        if (repaymentSchedule != null) {
            return ResponseEntity.ok(repaymentSchedule);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        String.format("وام با شناسه %d یافت نشد.", id)
                );
    }

    @GetMapping
    public ResponseEntity<List<RepaymentSchedule>> getAllRepaymentSchedule() {
        return ResponseEntity.ok(repaymentScheduleService.getAllRepaymentSchedule());
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<RepaymentSchedule>> getRepaymentScheduleByStatus(@PathVariable RepaymentScheduleStatus status) {
        List<RepaymentSchedule> repaymentSchedules = repaymentScheduleService.getFindByRepaymentScheduleStatus(status);
        return ResponseEntity.ok(repaymentSchedules);
    }

    @GetMapping("/duedate-id/{duedate}/{id}")
    public ResponseEntity<List<RepaymentSchedule>> getDueDateAndRepaymentScheduleStatus(@PathVariable LocalDate dueDate,
                                                                                        @PathVariable RepaymentScheduleStatus status) {
        List<RepaymentSchedule> repaymentSchedules = repaymentScheduleService
                .getFindByDueDateAndRepaymentScheduleStatus(dueDate, status);
        return ResponseEntity.ok(repaymentSchedules);
    }

    @GetMapping("/loan/{loanId}/count")
    public ResponseEntity<Long> countByLoanAndStatus(
            @PathVariable Long loanId,
            @RequestParam RepaymentScheduleStatus status) {

        Long count = repaymentScheduleService.getCountByLoanAndRepaymentScheduleStatus(loanId, status);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepaymentSchedule> updateRepaymentSchedule(
            @PathVariable Long id,
            @RequestParam(required = false) RepaymentScheduleStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {

        try {
            if (status == null && dueDate == null) {
                return ResponseEntity.badRequest().build();
            }

            RepaymentSchedule updatedSchedule = repaymentScheduleService.updateRepaymentSchedule(id, status, dueDate);
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<RepaymentSchedule> updateStatus(
            @PathVariable Long id,
            @PathVariable RepaymentScheduleStatus status) {

        try {
            repaymentScheduleService.updateStatus(id, status);
            RepaymentSchedule updatedSchedule = repaymentScheduleService.getRepaymentScheduleById(id);
            return ResponseEntity.ok(updatedSchedule);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
