package com.samadihadis.loanmanagementsystem.dto.repaymentSchedule;


import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class RepaymentScheduleResponse {


    private Long id;
    private Integer installmentNumber;
    private LocalDate dueDate;
    private BigDecimal totalInstallmentAmount;
    private RepaymentScheduleStatus repaymentScheduleStatus;
    private Long loanId;
}
