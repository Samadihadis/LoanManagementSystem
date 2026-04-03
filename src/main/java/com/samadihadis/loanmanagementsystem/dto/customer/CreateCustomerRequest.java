package com.samadihadis.loanmanagementsystem.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateCustomerRequest {

    @NotBlank(message = "نام نمی‌تواند خالی باشد")
    private String fullName;

    @NotBlank(message = "کد ملی نمی‌تواند خالی باشد")
    private String nationalId;

    @Email(message = "ایمیل معتبر نیست")
    private String email;

}
