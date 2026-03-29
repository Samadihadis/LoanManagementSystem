package com.samadihadis.loanmanagementsystem.controller;


import com.samadihadis.loanmanagementsystem.dto.EmailUpdateRequest;
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
    public ResponseEntity<Customer> createCustomer(@RequestBody @Validated Customer customer) {

        try {

            Customer newCustomer = new Customer();
            newCustomer.setFullName(customer.getFullName());
            newCustomer.setNationalId(customer.getNationalId());
            newCustomer.setEmail(customer.getEmail());

            var createCustomer = customerService.createCustomer(newCustomer);
            return ResponseEntity.ok(createCustomer);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {

        Customer customer = customerService.getCustomerById(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        String.format("کاربر با شناسه %d یافت نشد.", id)
                );
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

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        String.format("کاربر با کد ملی %s یافت نشد.", nationalId)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

        Customer customer = customerService.getCustomerById(id);

        if (customer != null) {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok()
                    .body(String.format("کاربر با شناسه %d حذف شد.", id));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        String.format("کاربر با شناسه %d یافت نشد.", id)
                );
    }

    @PutMapping({"/{id}/email"})
    public ResponseEntity<?> updateEmail(@PathVariable Long id, @RequestBody EmailUpdateRequest emailUpdateRequest) {

        try {
            Customer updateCustomer = customerService.updateEmail(id, emailUpdateRequest.getNewEmail());
            return ResponseEntity.ok(updateCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
