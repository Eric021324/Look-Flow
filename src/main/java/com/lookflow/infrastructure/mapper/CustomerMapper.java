package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;
import com.lookflow.infrastructure.dto.CustomerDto;
import com.lookflow.infrastructure.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "email", source = "email.value")
    CustomerDto toDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "email", qualifiedByName = "stringToEmail")
    Customer toDomain(CustomerDto customerDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    CustomerDto entityToDto(CustomerEntity customerEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    CustomerEntity dtoToEntity(CustomerDto customerDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "email", qualifiedByName = "stringToEmail")
    Customer entityToDomain(CustomerEntity customerEntity);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "email", source = "email.value")
    CustomerEntity domainToEntity(Customer customer);

    @Named("uuidToCustomerId")
    default CustomerId uuidToCustomerId(UUID uuid) {
        return new CustomerId(uuid);
    }

    @Named("stringToEmail")
    default Email stringToEmail(String email) {
        return new Email(email);
    }
}
