package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;

public interface UpdateCustomerUseCase {
    
    Customer updateCustomer(CustomerId customerId, String name, String phoneNumber, Email email);
}
