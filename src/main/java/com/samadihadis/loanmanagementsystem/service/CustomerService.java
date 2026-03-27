package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.entity.Customer;
import com.samadihadis.loanmanagementsystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    public Customer getCustomerByNationalId(String nationalId){
        Optional<Customer> customer = customerRepository.findByNationalId(nationalId);
        return customer.orElse(null);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    public Customer updateEmail(Long customerId , String newEmail){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("مشتری با شناسه %d یافت نشد." , customerId)
                ));

        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("ایمیل نمی‌تواند خالی باشد");
        }

        if (customerRepository.existsByEmail(newEmail)) {
            throw new IllegalStateException(
                    String.format( "ایمیل %s قبلا ثبت شده است." , newEmail)
            );
        }

        customer.setEmail(newEmail);

        return customerRepository.save(customer);
    }
}
