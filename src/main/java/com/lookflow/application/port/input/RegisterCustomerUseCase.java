package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.Email;

import java.time.LocalDate;

public interface RegisterCustomerUseCase {
    
    Customer registerCustomer(String name, String phoneNumber, Email email, LocalDate registerDate);
}
