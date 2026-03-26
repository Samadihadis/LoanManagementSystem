package com.samadihadis.loanmanagementsystem.repository;

import com.samadihadis.loanmanagementsystem.entity.Loan;
import com.samadihadis.loanmanagementsystem.entity.RepaymentSchedule;
import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {

    List<RepaymentSchedule> findByRepaymentScheduleStatus(RepaymentScheduleStatus repaymentScheduleStatus);

    List<RepaymentSchedule> findByDueDateAndRepaymentScheduleStatus(LocalDate date
            , RepaymentScheduleStatus status);  //پیدا کردن اقساطی که امروز سررسید می شود

    long countByLoanAndRepaymentScheduleStatus(Loan loan, RepaymentScheduleStatus status);  //شمارش تعداداقساط پرداخت شده یک وام

}
