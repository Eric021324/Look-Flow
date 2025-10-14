package com.lookflow.application.port.input;

import com.lookflow.domain.model.valueobject.ServiceId;

public interface ActivateServiceUseCase {
    
    void activateService(ServiceId serviceId);
    
    void deactivateService(ServiceId serviceId);
}
