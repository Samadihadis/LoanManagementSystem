package com.samadihadis.loanmanagementsystem.dto.repaymentSchedule;


import com.samadihadis.loanmanagementsystem.enums.RepaymentScheduleStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class CreateRepaymentScheduleRequest {

    @NotNull
    private Integer installmentNumber;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Positive
    private BigDecimal totalInstallmentAmount;

    private RepaymentScheduleStatus repaymentScheduleStatus;
}
