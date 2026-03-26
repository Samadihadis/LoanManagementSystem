package com.samadihadis.loanmanagementsystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String nationalId;

    @Email
    private String email;

}
