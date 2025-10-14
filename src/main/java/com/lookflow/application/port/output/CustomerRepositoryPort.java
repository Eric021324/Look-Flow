package com.lookflow.application.port.output;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    
    Customer save(Customer customer);
    
    Optional<Customer> findById(CustomerId customerId);
    
    Optional<Customer> findByEmail(Email email);
    
    List<Customer> findAll();
    
    List<Customer> findByName(String name);
    
    void delete(CustomerId customerId);
    
    boolean existsById(CustomerId customerId);
    
    boolean existsByEmail(Email email);
}
