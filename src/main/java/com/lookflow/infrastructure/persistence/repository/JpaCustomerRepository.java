package com.lookflow.infrastructure.persistence.repository;

import com.lookflow.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByEmail(String email);

    List<CustomerEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM CustomerEntity c WHERE c.name LIKE %:name%")
    List<CustomerEntity> findByName(@Param("name") String name);

    boolean existsByEmail(String email);
}

