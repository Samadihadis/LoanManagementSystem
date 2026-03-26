package com.samadihadis.loanmanagementsystem.repository;

import com.samadihadis.loanmanagementsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByNationalId(String nationalId);
}
