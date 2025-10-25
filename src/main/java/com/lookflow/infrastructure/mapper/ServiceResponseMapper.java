package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.infrastructure.dto.response.ServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ServiceResponseMapper {

    ServiceResponseMapper INSTANCE = Mappers.getMapper(ServiceResponseMapper.class);

    @Mapping(target = "id", expression = "java(service.getId().getValue())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "cost", expression = "java(service.getCost().getAmount())")
    @Mapping(target = "serviceCategory", expression = "java(service.getServiceCategory().name())")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServiceResponse toResponse(Service service);
}
