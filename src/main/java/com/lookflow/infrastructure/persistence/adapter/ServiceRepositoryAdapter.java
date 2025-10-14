package com.lookflow.infrastructure.persistence.adapter;

import com.lookflow.application.port.output.ServiceRepositoryPort;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;
import com.lookflow.infrastructure.mapper.ServiceMapper;
import com.lookflow.infrastructure.persistence.entity.ServiceEntity;
import com.lookflow.infrastructure.persistence.repository.JpaServiceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceRepositoryAdapter implements ServiceRepositoryPort {

    private final JpaServiceRepository jpaServiceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceRepositoryAdapter(JpaServiceRepository jpaServiceRepository,
                                  ServiceMapper serviceMapper) {
        this.jpaServiceRepository = jpaServiceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public Service save(Service service) {
        ServiceEntity serviceEntity = serviceMapper.domainToEntity(service);
        serviceEntity.setCreatedAt(LocalDateTime.now());
        serviceEntity.setUpdatedAt(LocalDateTime.now());
        
        ServiceEntity savedEntity = jpaServiceRepository.save(serviceEntity);
        return serviceMapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Service> findById(ServiceId serviceId) {
        return jpaServiceRepository.findById(serviceId.getValue())
                .map(serviceMapper::entityToDomain);
    }

    @Override
    public List<Service> findAll() {
        List<ServiceEntity> entities = jpaServiceRepository.findAll();
        return entities.stream()
                .map(serviceMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Service> findActiveServices() {
        List<ServiceEntity> entities = jpaServiceRepository.findByActiveTrue();
        return entities.stream()
                .map(serviceMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Service> findByCategory(ServiceCategory serviceCategory) {
        List<ServiceEntity> entities = jpaServiceRepository.findByServiceCategory(serviceCategory.name());
        return entities.stream()
                .map(serviceMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Service> findByName(String name) {
        List<ServiceEntity> entities = jpaServiceRepository.findByName(name);
        return entities.stream()
                .map(serviceMapper::entityToDomain)
                .toList();
    }

    @Override
    public void delete(ServiceId serviceId) {
        jpaServiceRepository.deleteById(serviceId.getValue());
    }

    @Override
    public boolean existsById(ServiceId serviceId) {
        return jpaServiceRepository.existsById(serviceId.getValue());
    }
}
