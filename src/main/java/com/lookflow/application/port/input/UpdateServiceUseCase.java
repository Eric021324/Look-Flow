package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;

public interface UpdateServiceUseCase {
    
    Service updateService(ServiceId serviceId, String name, String description, 
                         int duration, Money cost, ServiceCategory serviceCategory, 
                         boolean active);
}
