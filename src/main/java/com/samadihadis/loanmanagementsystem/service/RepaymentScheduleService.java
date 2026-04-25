package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.dto.repaymentSchedule.RepaymentScheduleResponse;
import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.entity.RepaymentSchedule;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanNotFoundException;
import com.samadihadis.loanmanagementsystem.exception.loan.LoanStatusException;
import com.samadihadis.loanmanagementsystem.exception.repaymentSchedule.InvalidInstallmentAmountException;
import com.samadihadis.loanmanagementsystem.exception.repaymentSchedule.RepaymentScheduleNotFoundException;
import com.samadihadis.loanmanagementsystem.repository.LoanRepository;
import com.samadihadis.loanmanagementsystem.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanRepository loanRepository;


    public RepaymentSchedule createRepaymentSchedule(Long loanId, RepaymentSchedule repaymentSchedule) {

        if (repaymentSchedule.getTotalInstallmentAmount() == null ||
                repaymentSchedule.getTotalInstallmentAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInstallmentAmountException("مبلغ قسط باید مثبت باشد.");
        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        if (loan.getLoanStatus() == LoanStatus.PAID) {
            throw new LoanStatusException("وام قبلا تسویه شده و نمی‌توان قسط جدید ثبت کرد.");
        }

        repaymentSchedule.setLoan(loan);

        return repaymentScheduleRepository.save(repaymentSchedule);
    }

    public RepaymentSchedule getRepaymentScheduleById(Long id) {
        return repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(
                        String.format("قسط با شناسه %d یافت نشد.", id)));
    }

    public List<RepaymentSchedule> getAllRepaymentSchedule() {
        return repaymentScheduleRepository.findAll();
    }

    public void deleteRepaymentSchedule(Long id) {
        repaymentScheduleRepository.deleteById(id);
    }

    public List<RepaymentSchedule> getFindByRepaymentScheduleStatus(RepaymentScheduleStatus repaymentScheduleStatus) {
        return repaymentScheduleRepository.findByRepaymentScheduleStatus(repaymentScheduleStatus);
    }

    public List<RepaymentSchedule> getFindByDueDateAndRepaymentScheduleStatus(LocalDate dueDate,
                                                                              RepaymentScheduleStatus repaymentScheduleStatus) {

        return repaymentScheduleRepository.findByDueDateAndRepaymentScheduleStatus(dueDate, repaymentScheduleStatus);
    }

    public Long getCountByLoanAndRepaymentScheduleStatus(Long loanId , RepaymentScheduleStatus repaymentScheduleStatus) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        return repaymentScheduleRepository.countByLoanAndRepaymentScheduleStatus(loan , repaymentScheduleStatus);
    }

    public RepaymentSchedule updateRepaymentSchedule (Long id , RepaymentScheduleStatus newStatus , LocalDate newDueDate) {

        RepaymentSchedule repaymentSchedule = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException(
                        String.format("قسط با شناسه %d یافت نشد.", id)));

        repaymentSchedule.setRepaymentScheduleStatus(newStatus);
        repaymentSchedule.setDueDate(newDueDate);

        return repaymentScheduleRepository.save(repaymentSchedule);
    }

    public void updateStatus(Long id, RepaymentScheduleStatus repaymentScheduleStatus) {

        RepaymentSchedule repaymentSchedule = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException("قسط پیدا نشد"));

        repaymentSchedule.setRepaymentScheduleStatus(repaymentScheduleStatus);
    }

    public RepaymentScheduleResponse toResponse(RepaymentSchedule schedule) {

        RepaymentScheduleResponse response = new RepaymentScheduleResponse();

        response.setId(schedule.getId());
        response.setInstallmentNumber(schedule.getInstallmentNumber());
        response.setDueDate(schedule.getDueDate());
        response.setTotalInstallmentAmount(schedule.getTotalInstallmentAmount());
        response.setRepaymentScheduleStatus(schedule.getRepaymentScheduleStatus());

        if (schedule.getLoan() != null) {
            response.setLoanId(schedule.getLoan().getId());
        }

        return response;
    }


}
