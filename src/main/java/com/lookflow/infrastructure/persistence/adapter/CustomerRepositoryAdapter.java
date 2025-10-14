package com.lookflow.infrastructure.persistence.adapter;

import com.lookflow.application.port.output.CustomerRepositoryPort;
import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;
import com.lookflow.infrastructure.mapper.CustomerMapper;
import com.lookflow.infrastructure.persistence.entity.CustomerEntity;
import com.lookflow.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryAdapter(JpaCustomerRepository jpaCustomerRepository,
                                   CustomerMapper customerMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = customerMapper.domainToEntity(customer);
        customerEntity.setCreatedAt(LocalDateTime.now());
        customerEntity.setUpdatedAt(LocalDateTime.now());
        
        CustomerEntity savedEntity = jpaCustomerRepository.save(customerEntity);
        return customerMapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return jpaCustomerRepository.findById(customerId.getValue())
                .map(customerMapper::entityToDomain);
    }

    @Override
    public Optional<Customer> findByEmail(Email email) {
        return jpaCustomerRepository.findByEmail(email.getValue())
                .map(customerMapper::entityToDomain);
    }

    @Override
    public List<Customer> findAll() {
        List<CustomerEntity> entities = jpaCustomerRepository.findAll();
        return entities.stream()
                .map(customerMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Customer> findByName(String name) {
        List<CustomerEntity> entities = jpaCustomerRepository.findByName(name);
        return entities.stream()
                .map(customerMapper::entityToDomain)
                .toList();
    }

    @Override
    public void delete(CustomerId customerId) {
        jpaCustomerRepository.deleteById(customerId.getValue());
    }

    @Override
    public boolean existsById(CustomerId customerId) {
        return jpaCustomerRepository.existsById(customerId.getValue());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaCustomerRepository.existsByEmail(email.getValue());
    }
}
