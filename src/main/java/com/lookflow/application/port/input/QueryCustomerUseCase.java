package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;

import java.util.List;
import java.util.Optional;

public interface QueryCustomerUseCase {
    
    Optional<Customer> getCustomerById(CustomerId customerId);
    
    Optional<Customer> getCustomerByEmail(Email email);
    
    List<Customer> getAllCustomers();
    
    List<Customer> getCustomersByName(String name);
}
