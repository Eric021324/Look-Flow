package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.infrastructure.dto.EmployeeDto;
import com.lookflow.infrastructure.persistence.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, WorkShiftMapper.class})
public interface EmployeeMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "serviceIds", source = "services", qualifiedByName = "servicesToUuids")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToEmployeeId")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "services", source = "serviceIds", qualifiedByName = "uuidsToServices")
    Employee toDomain(EmployeeDto employeeDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "serviceIds", source = "serviceIds")
    EmployeeDto entityToDto(EmployeeEntity employeeEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "serviceIds", source = "serviceIds")
    EmployeeEntity dtoToEntity(EmployeeDto employeeDto);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToEmployeeId")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "services", source = "serviceIds", qualifiedByName = "uuidsToServices")
    Employee entityToDomain(EmployeeEntity employeeEntity);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "workShifts", source = "workShifts")
    @Mapping(target = "serviceIds", source = "services", qualifiedByName = "servicesToUuids")
    EmployeeEntity domainToEntity(Employee employee);

    @Named("servicesToUuids")
    default List<UUID> servicesToUuids(List<Service> services) {
        return services.stream()
                .map(service -> service.getId().getValue())
                .collect(Collectors.toList());
    }

    @Named("uuidsToServices")
    default List<Service> uuidsToServices(List<UUID> uuids) {
        // This would need to be implemented based on your service repository
        // For now, returning empty list as it should be handled by the domain
        return List.of();
    }

    @Named("uuidToEmployeeId")
    default EmployeeId uuidToEmployeeId(UUID uuid) {
        return new EmployeeId(uuid);
    }
}
