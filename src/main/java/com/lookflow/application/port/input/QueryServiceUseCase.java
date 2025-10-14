package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;

import java.util.List;
import java.util.Optional;

public interface QueryServiceUseCase {
    
    Optional<Service> getServiceById(ServiceId serviceId);
    
    List<Service> getAllServices();
    
    List<Service> getActiveServices();
    
    List<Service> getServicesByCategory(ServiceCategory serviceCategory);
    
    List<Service> getServicesByName(String name);
}
