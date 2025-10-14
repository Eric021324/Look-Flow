package com.lookflow.infrastructure.persistence.repository;

import com.lookflow.infrastructure.persistence.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaEmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    Optional<EmployeeEntity> findByEmail(String email);

    List<EmployeeEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.name LIKE %:name%")
    List<EmployeeEntity> findByName(@Param("name") String name);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.active = true")
    List<EmployeeEntity> findAvailableEmployees();

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(e) > 0 FROM EmployeeEntity e WHERE e.email = :email AND e.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);
}

