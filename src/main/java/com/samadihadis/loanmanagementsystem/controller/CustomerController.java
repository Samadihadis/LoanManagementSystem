package com.samadihadis.loanmanagementsystem.controller;


import com.samadihadis.loanmanagementsystem.dto.customer.CreateCustomerRequest;
import com.samadihadis.loanmanagementsystem.dto.customer.CustomerResponse;
import com.samadihadis.loanmanagementsystem.dto.customer.EmailUpdateRequest;
import com.samadihadis.loanmanagementsystem.entity.Customer;
import com.samadihadis.loanmanagementsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Validated CreateCustomerRequest request) {

            Customer newCustomer = new Customer();
            newCustomer.setFullName(request.getFullName());
            newCustomer.setNationalId(request.getNationalId());
            newCustomer.setEmail(request.getEmail());

            Customer saved = customerService.createCustomer(newCustomer);

            CustomerResponse response = new CustomerResponse();
            response.setId(saved.getId());
            response.setFullName(saved.getFullName());
            response.setNationalId(saved.getNationalId());
            response.setEmail(saved.getEmail());

            return ResponseEntity.ok(response);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {

        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        return ResponseEntity.ok("تعداد مشتری‌ها: " + customerService.getAllCustomers().size());
    }

    @GetMapping("/national-id/{nationalId}")
    public ResponseEntity<?> getCustomerByNationalId(@PathVariable String nationalId) {

        Customer customer = customerService.getCustomerByNationalId(nationalId);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);
        return ResponseEntity.ok(String.format("کاربر با شناسه %d حذف شد.", id));
    }

    @PutMapping({"/{id}/email"})
    public ResponseEntity<?> updateEmail(@PathVariable Long id, @RequestBody EmailUpdateRequest emailUpdateRequest) {

        Customer updatedCustomer = customerService.updateEmail(id, emailUpdateRequest.getNewEmail());
        return ResponseEntity.ok(updatedCustomer);
    }

}
