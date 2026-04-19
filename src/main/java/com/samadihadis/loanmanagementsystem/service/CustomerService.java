package com.samadihadis.loanmanagementsystem.service;


import com.samadihadis.loanmanagementsystem.entity.Customer;
import com.samadihadis.loanmanagementsystem.exception.customer.CustomerNotFoundException;
import com.samadihadis.loanmanagementsystem.exception.customer.DuplicateEmailException;
import com.samadihadis.loanmanagementsystem.exception.customer.InvalidEmailException;
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
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("مشتری با شناسه %d یافت نشد.", id)
                ));
    }

    public Customer getCustomerByNationalId(String nationalId){
        return customerRepository.findByNationalId(nationalId)
                .orElseThrow(()-> new CustomerNotFoundException(
                        String.format("مشتری با کد ملی %s یافت نشد.", nationalId)
                ));
    }

    public void deleteCustomer(Long id){
        getCustomerById(id);
        customerRepository.deleteById(id);
    }

    public Customer updateEmail(Long customerId , String newEmail){

        Customer customer = getCustomerById(customerId);

        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new InvalidEmailException("ایمیل نمی‌تواند خالی باشد");
        }

        if (customerRepository.existsByEmail(newEmail)) {
            throw new DuplicateEmailException(
                    String.format( "ایمیل %s قبلا ثبت شده است." , newEmail)
            );
        }
        customer.setEmail(newEmail);
        return customerRepository.save(customer);
    }
}
