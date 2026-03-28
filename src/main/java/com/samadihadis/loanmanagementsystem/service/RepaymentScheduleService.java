package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.entity.RepaymentSchedule;
import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
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
            throw new RuntimeException("مبلغ پرداختی باید مثبت باشد.");
        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        if (loan.getLoanStatus() == LoanStatus.PAID) {
            throw new RuntimeException("وام قبلا تسویه شده و نمیتوان پرداختی ثبت کرد.");
        }

        repaymentSchedule.setLoan(loan);

        return repaymentScheduleRepository.save(repaymentSchedule);
    }

    public RepaymentSchedule getRepaymentScheduleById(Long id) {
        Optional<RepaymentSchedule> repaymentSchedule = repaymentScheduleRepository.findById(id);
        return repaymentSchedule.orElse(null);
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


    public List<RepaymentSchedule> getFindByDueDateAndRepaymentScheduleStatus(LocalDate localDate,
                                                                              RepaymentScheduleStatus repaymentScheduleStatus) {

        return repaymentScheduleRepository.findByDueDateAndRepaymentScheduleStatus(localDate, repaymentScheduleStatus);
    }

    public Long getCountByLoanAndRepaymentScheduleStatus(Long loanId , RepaymentScheduleStatus repaymentScheduleStatus) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("وام با شناسه %d یافت نشد.", loanId)));

        return repaymentScheduleRepository.countByLoanAndRepaymentScheduleStatus(loan , repaymentScheduleStatus);
    }

    public RepaymentSchedule updateRepaymentSchedule (Long id , RepaymentScheduleStatus newStatus , LocalDate newDueDate) {

        RepaymentSchedule repaymentSchedule = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("قسط با شناسه %d یافت نشد." , id)));

        repaymentSchedule.setRepaymentScheduleStatus(newStatus);
        repaymentSchedule.setDueDate(newDueDate);

        return repaymentScheduleRepository.save(repaymentSchedule);
    }

    public void updateStatus(Long id, RepaymentScheduleStatus repaymentScheduleStatus) {

        RepaymentSchedule repaymentSchedule = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("قسط پیدا نشد"));

        repaymentSchedule.setRepaymentScheduleStatus(repaymentScheduleStatus);
    }

}
