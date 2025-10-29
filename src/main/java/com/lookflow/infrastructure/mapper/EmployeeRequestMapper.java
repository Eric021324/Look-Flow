package com.lookflow.infrastructure.mapper;

import java.time.DayOfWeek;
import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Address;
import com.lookflow.domain.model.valueobject.WorkShift;
import com.lookflow.infrastructure.dto.request.AddressRequest;
import com.lookflow.infrastructure.dto.request.RegisterEmployeeRequest;
import com.lookflow.infrastructure.dto.request.WorkShiftRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EmployeeRequestMapper {

    EmployeeRequestMapper INSTANCE = Mappers.getMapper(EmployeeRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstSurname", source = "firstSurname")
    @Mapping(target = "secondSurname", source = "secondSurname")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Employee toDomain(RegisterEmployeeRequest request);

    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    Address toAddress(AddressRequest addressRequest);

    @Mapping(target = "dayOfWeek",ignore = true)
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShift toWorkShift(WorkShiftRequest workShiftRequest);

    default List<Service> mapServices(List<UUID> serviceIds) {
        // This would typically fetch services from repository
        // For now, return empty list as services should be fetched separately
        return List.of();
    }
}
