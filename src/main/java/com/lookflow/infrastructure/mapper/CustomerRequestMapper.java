package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.Email;
import com.lookflow.infrastructure.dto.request.RegisterCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    CustomerRequestMapper INSTANCE = Mappers.getMapper(CustomerRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "registerDate", source = "registerDate")
    Customer toDomain(RegisterCustomerRequest request);
}
