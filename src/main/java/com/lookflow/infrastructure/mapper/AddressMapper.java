package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.valueobject.Address;
import com.lookflow.infrastructure.dto.AddressDto;
import com.lookflow.infrastructure.persistence.entity.AddressEmbeddable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address address);

    Address toDomain(AddressDto addressDto);

    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    AddressDto embeddableToDto(AddressEmbeddable addressEmbeddable);

    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    AddressEmbeddable dtoToEmbeddable(AddressDto addressDto);
}

