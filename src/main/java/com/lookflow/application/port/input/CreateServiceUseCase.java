package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.ServiceCategory;

public interface CreateServiceUseCase {
    
    Service createService(String name, String description, int duration, Money cost, 
                         ServiceCategory serviceCategory, boolean active);
}
