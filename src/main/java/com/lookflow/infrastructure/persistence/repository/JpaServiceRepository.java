package com.lookflow.infrastructure.persistence.repository;

import com.lookflow.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaServiceRepository extends JpaRepository<ServiceEntity, UUID> {

    List<ServiceEntity> findByActiveTrue();

    List<ServiceEntity> findByServiceCategory(String serviceCategory);

    List<ServiceEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM ServiceEntity s WHERE s.name LIKE %:name%")
    List<ServiceEntity> findByName(@Param("name") String name);

    boolean existsByName(String name);
}
