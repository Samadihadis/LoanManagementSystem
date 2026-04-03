package com.samadihadis.loanmanagementsystem.dto.loan;

import com.samadihadis.loanmanagementsystem.enums.LoanType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class CreateLoanRequest {

    @NotNull
    @Positive
    private BigDecimal principalAmount;

    @NotNull
    private BigDecimal interestRate;

    @Min(6)
    @Max(36)
    private Integer term;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LoanType loanType;

    @NotNull
    private Long customerId;

}
