package com.lookflow.application.port.output;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;

import java.util.List;
import java.util.Optional;

public interface ServiceRepositoryPort {
    
    Service save(Service service);
    
    Optional<Service> findById(ServiceId serviceId);
    
    List<Service> findAll();
    
    List<Service> findActiveServices();
    
    List<Service> findByCategory(ServiceCategory serviceCategory);
    
    List<Service> findByName(String name);
    
    void delete(ServiceId serviceId);
    
    boolean existsById(ServiceId serviceId);
}
