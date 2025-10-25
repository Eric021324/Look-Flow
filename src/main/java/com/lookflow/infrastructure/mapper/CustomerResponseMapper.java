package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.infrastructure.dto.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {

    CustomerResponseMapper INSTANCE = Mappers.getMapper(CustomerResponseMapper.class);

    @Mapping(target = "id", expression = "java(customer.getId().getValue())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", expression = "java(customer.getEmail().getValue())")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "registerDate", source = "registerDate")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CustomerResponse toResponse(Customer customer);
}
