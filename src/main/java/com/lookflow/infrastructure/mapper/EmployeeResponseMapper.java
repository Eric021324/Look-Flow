package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.valueobject.Address;
import com.lookflow.domain.model.valueobject.WorkShift;
import com.lookflow.infrastructure.dto.response.AddressResponse;
import com.lookflow.infrastructure.dto.response.EmployeeResponse;
import com.lookflow.infrastructure.dto.response.WorkShiftResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {

    EmployeeResponseMapper INSTANCE = Mappers.getMapper(EmployeeResponseMapper.class);

    @Mapping(target = "id", expression = "java(employee.getId().getValue())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstSurname", source = "firstSurname")
    @Mapping(target = "secondSurname", source = "secondSurname")
    @Mapping(target = "email", expression = "java(employee.getEmail().getValue())")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "serviceIds", expression = "java(mapServiceIds(employee.getServices()))")
    @Mapping(target = "rol", expression = "java(employee.getRol() != null ? employee.getRol().name() : null)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    AddressResponse toAddressResponse(Address address);

    @Mapping(target = "dayOfWeek", expression = "java(workShift.getDayOfWeek().name())")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftResponse toWorkShiftResponse(WorkShift workShift);

    default List<UUID> mapServiceIds(List<com.lookflow.domain.model.entity.Service> services) {
        return services.stream()
                .map(service -> service.getId().getValue())
                .toList();
    }
}
