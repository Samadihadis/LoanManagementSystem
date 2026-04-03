package com.samadihadis.loanmanagementsystem.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String fullName;
    private String nationalId;
    private String email;
}
