package com.lookflow.application.service;

import com.lookflow.application.port.input.*;
import com.lookflow.application.port.output.CustomerRepositoryPort;
import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements RegisterCustomerUseCase, UpdateCustomerUseCase, QueryCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public CustomerService(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerCustomer(String name, String phoneNumber, Email email, LocalDate registerDate) {
        
        // Validar que el email no esté ya registrado
        if (customerRepository.existsByEmail(email)) {
            throw new DomainException("Customer with email " + email + " already exists");
        }
        
        // Validar que la fecha de registro no sea en el futuro
        if (registerDate.isAfter(LocalDate.now())) {
            throw new DomainException("Registration date cannot be in the future");
        }
        
        Customer customer = new Customer(registerDate, phoneNumber, email, name);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(CustomerId customerId, String name, String phoneNumber, Email email) {
        
        Customer customer = getCustomerByIdOrThrow(customerId);
        
        // Validar que el nuevo email no esté ya registrado por otro cliente
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(customerId)) {
            throw new DomainException("Email " + email + " is already registered by another customer");
        }
        
        // Actualizar los datos del cliente
        customer = new Customer(customer.getRegisterDate(), phoneNumber, email, name);
        // Mantener el mismo ID - se maneja automáticamente por el repositorio
        
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(CustomerId customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(Email email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getCustomersByName(String name) {
        return customerRepository.findByName(name);
    }

    private Customer getCustomerByIdOrThrow(CustomerId customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new DomainException("Customer not found with ID: " + customerId);
        }
        return customer.get();
    }
}
