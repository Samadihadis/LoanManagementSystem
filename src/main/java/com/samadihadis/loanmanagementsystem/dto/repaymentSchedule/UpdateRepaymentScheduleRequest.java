package com.samadihadis.loanmanagementsystem.dto.repaymentSchedule;

import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateRepaymentScheduleRequest {

    private RepaymentScheduleStatus status;
    private LocalDate dueDate;
}
