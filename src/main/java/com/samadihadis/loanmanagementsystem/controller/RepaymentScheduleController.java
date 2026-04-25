package com.samadihadis.loanmanagementsystem.controller;

import com.samadihadis.loanmanagementsystem.dto.repaymentSchedule.CreateRepaymentScheduleRequest;
import com.samadihadis.loanmanagementsystem.dto.repaymentSchedule.RepaymentScheduleResponse;
import com.samadihadis.loanmanagementsystem.dto.repaymentSchedule.UpdateRepaymentScheduleRequest;
import com.samadihadis.loanmanagementsystem.entity.RepaymentSchedule;
import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import com.samadihadis.loanmanagementsystem.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<RepaymentScheduleResponse> createRepaymentSchedule(
            @RequestBody @Validated CreateRepaymentScheduleRequest request,
            @PathVariable Long loanId) {

        RepaymentSchedule schedule = new RepaymentSchedule();
        schedule.setInstallmentNumber(request.getInstallmentNumber());
        schedule.setDueDate(request.getDueDate());
        schedule.setTotalInstallmentAmount(request.getTotalInstallmentAmount());
        schedule.setRepaymentScheduleStatus(request.getRepaymentScheduleStatus());

        RepaymentSchedule saved = repaymentScheduleService.createRepaymentSchedule(loanId, schedule);
        return ResponseEntity.ok(repaymentScheduleService.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepaymentScheduleResponse> getRepaymentScheduleById(@PathVariable Long id) {
        RepaymentSchedule repaymentSchedule = repaymentScheduleService.getRepaymentScheduleById(id);
        return ResponseEntity.ok(repaymentScheduleService.toResponse(repaymentSchedule));
    }

    @GetMapping
    public ResponseEntity<List<RepaymentScheduleResponse>> getAllRepaymentSchedule() {
        List<RepaymentScheduleResponse> result = repaymentScheduleService.getAllRepaymentSchedule()
                .stream()
                .map(repaymentScheduleService::toResponse)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RepaymentSchedule>> getRepaymentScheduleByStatus(
            @PathVariable RepaymentScheduleStatus status) {
        return ResponseEntity.ok(repaymentScheduleService.getFindByRepaymentScheduleStatus(status));
    }

    @GetMapping("/duedate-status/{dueDate}/{status}")
    public ResponseEntity<List<RepaymentSchedule>> getDueDateAndRepaymentScheduleStatus(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @PathVariable RepaymentScheduleStatus status) {
        return ResponseEntity.ok(repaymentScheduleService.getFindByDueDateAndRepaymentScheduleStatus(dueDate, status));
    }

    @GetMapping("/loan/{loanId}/count")
    public ResponseEntity<Long> countByLoanAndStatus(
            @PathVariable Long loanId,
            @RequestParam RepaymentScheduleStatus status) {
        return ResponseEntity.ok(repaymentScheduleService.getCountByLoanAndRepaymentScheduleStatus(loanId, status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepaymentScheduleResponse> updateRepaymentSchedule(
            @PathVariable Long id,
            @RequestBody UpdateRepaymentScheduleRequest request) {

        RepaymentSchedule updated = repaymentScheduleService.updateRepaymentSchedule(
                id, request.getStatus(), request.getDueDate());
        return ResponseEntity.ok(repaymentScheduleService.toResponse(updated));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<RepaymentSchedule> updateStatus(
            @PathVariable Long id,
            @PathVariable RepaymentScheduleStatus status) {

        repaymentScheduleService.updateStatus(id, status);
        RepaymentSchedule updatedSchedule = repaymentScheduleService.getRepaymentScheduleById(id);
        return ResponseEntity.ok(updatedSchedule);
    }
}