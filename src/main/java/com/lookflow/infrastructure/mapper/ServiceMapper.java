package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;
import com.lookflow.infrastructure.dto.ServiceDto;
import com.lookflow.infrastructure.persistence.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "cost", source = "cost.amount")
    @Mapping(target = "serviceCategory", source = "serviceCategory", qualifiedByName = "serviceCategoryToString")
    ServiceDto toDto(Service service);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cost", source = "cost", qualifiedByName = "bigDecimalToMoney")
    @Mapping(target = "serviceCategory", source = "serviceCategory", qualifiedByName = "stringToServiceCategory")
    Service toDomain(ServiceDto serviceDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "serviceCategory", source = "serviceCategory")
    ServiceDto entityToDto(ServiceEntity serviceEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "serviceCategory", source = "serviceCategory")
    ServiceEntity dtoToEntity(ServiceDto serviceDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cost", source = "cost", qualifiedByName = "bigDecimalToMoney")
    @Mapping(target = "serviceCategory", source = "serviceCategory", qualifiedByName = "stringToServiceCategory")
    Service entityToDomain(ServiceEntity serviceEntity);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "cost", source = "cost.amount")
    @Mapping(target = "serviceCategory", source = "serviceCategory", qualifiedByName = "serviceCategoryToString")
    ServiceEntity domainToEntity(Service service);

    @Named("uuidToServiceId")
    default ServiceId uuidToServiceId(UUID uuid) {
        return new ServiceId(uuid);
    }

    @Named("bigDecimalToMoney")
    default Money bigDecimalToMoney(BigDecimal amount) {
        return new Money(amount);
    }

    @Named("serviceCategoryToString")
    default String serviceCategoryToString(ServiceCategory serviceCategory) {
        return serviceCategory.name();
    }

    @Named("stringToServiceCategory")
    default ServiceCategory stringToServiceCategory(String serviceCategory) {
        return ServiceCategory.valueOf(serviceCategory);
    }
}
