package com.example.demo.service;

import com.example.demo.Repository.CustomerRepository;
import com.example.demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service

public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    public Optional<Customer> getCustomerWithAppointments(Long id) {
        return customerRepository.findByIdWithAppointments(id);
    }
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    public boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
