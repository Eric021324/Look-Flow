package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.infrastructure.dto.request.CreateServiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

    ServiceRequestMapper INSTANCE = Mappers.getMapper(ServiceRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "cost", expression = "java(new Money(request.getCost()))")
    @Mapping(target = "serviceCategory", expression = "java(ServiceCategory.valueOf(request.getServiceCategory()))")
    @Mapping(target = "active", source = "active")
    Service toDomain(CreateServiceRequest request);
}
