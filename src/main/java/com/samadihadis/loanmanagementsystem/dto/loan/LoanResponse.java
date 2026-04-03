package com.samadihadis.loanmanagementsystem.dto.loan;

import com.samadihadis.loanmanagementsystem.enums.LoanStatus;
import com.samadihadis.loanmanagementsystem.enums.LoanType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class LoanResponse {

    private Long id;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer term;
    private LocalDate startDate;
    private LocalDate maturityDate;
    private LoanStatus loanStatus;
    private LoanType loanType;
    private Long customerId;

}
