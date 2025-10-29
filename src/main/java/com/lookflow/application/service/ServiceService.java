package com.lookflow.application.service;

import com.lookflow.application.port.input.*;
import com.lookflow.application.port.output.ServiceRepositoryPort;
import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService implements CreateServiceUseCase, UpdateServiceUseCase, 
        ActivateServiceUseCase, QueryServiceUseCase {

    private final ServiceRepositoryPort serviceRepository;

    public ServiceService(ServiceRepositoryPort serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service createService(String name, String description, int duration, Money cost, 
                               ServiceCategory serviceCategory, boolean active) {
        
        // Validar que el nombre del servicio no esté ya registrado
        if (serviceRepository.findByName(name).size() > 0) {
            throw new DomainException("Service with name '" + name + "' already exists");
        }
        
        // Validar que el costo sea mayor que cero
        if (!cost.isGreaterThanZero()) {
            throw new DomainException("Service cost must be greater than zero");
        }
        
        // Validar que la duración sea positiva
        if (duration <= 0) {
            throw new DomainException("Service duration must be greater than zero");
        }
        
        Service service = new Service(name, description, duration, cost, serviceCategory, active);
        return serviceRepository.save(service);
    }

    @Override
    public Service updateService(ServiceId serviceId, String name, String description, 
                               int duration, Money cost, ServiceCategory serviceCategory, 
                               boolean active) {
        
        Service service = getServiceByIdOrThrow(serviceId);
        
        // Validar que el nuevo nombre no esté ya registrado por otro servicio
        List<Service> existingServices = serviceRepository.findByName(name);
        if (existingServices.size() > 0 && !existingServices.get(0).getId().equals(serviceId)) {
            throw new DomainException("Service with name '" + name + "' already exists");
        }
        
        // Validar que el costo sea mayor que cero
        if (!cost.isGreaterThanZero()) {
            throw new DomainException("Service cost must be greater than zero");
        }
        
        // Validar que la duración sea positiva
        if (duration <= 0) {
            throw new DomainException("Service duration must be greater than zero");
        }
        
        // Actualizar el servicio
        service.setName(name);
        service.setDescription(description);
        service.setDuration(duration);
        service.setCost(cost);
        service.setServiceCategory(serviceCategory);
        service.setActive(active);
        
        return serviceRepository.save(service);
    }

    @Override
    public Service activateService(ServiceId serviceId) {
        Service service = getServiceByIdOrThrow(serviceId);
        service.setActive(true);
        serviceRepository.save(service);
        return service;
    }

    @Override
    public void deactivateService(ServiceId serviceId) {
        Service service = getServiceByIdOrThrow(serviceId);
        service.setActive(false);
        serviceRepository.save(service);
    }

    @Override
    public Optional<Service> getServiceById(ServiceId serviceId) {
        return serviceRepository.findById(serviceId);
    }

    @Override
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public List<Service> getActiveServices() {
        return serviceRepository.findActiveServices();
    }

    @Override
    public List<Service> getServicesByCategory(ServiceCategory serviceCategory) {
        return serviceRepository.findByCategory(serviceCategory);
    }

    @Override
    public List<Service> getServicesByName(String name) {
        return serviceRepository.findByName(name);
    }

    private Service getServiceByIdOrThrow(ServiceId serviceId) {
        Optional<Service> service = serviceRepository.findById(serviceId);
        if (service.isEmpty()) {
            throw new DomainException("Service not found with ID: " + serviceId);
        }
        return service.get();
    }
}
