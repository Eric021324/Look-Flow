package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.ServiceId;

public interface ActivateServiceUseCase {
    
    Service activateService(ServiceId serviceId);
    
    void deactivateService(ServiceId serviceId);
}
