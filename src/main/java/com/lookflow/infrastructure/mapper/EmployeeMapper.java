package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Email;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.Rol;
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
        @Mapping(target = "email", ignore = true)
        @Mapping(target = "rol", ignore = true)
        EmployeeDto toDto(Employee employee);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "address", source = "address")
        @Mapping(target = "workShifts", source = "workShifts")
        @Mapping(target = "services", source = "serviceIds", qualifiedByName = "uuidsToServices")
        @Mapping(target = "email", ignore = true)
        @Mapping(target = "rol", ignore = true)
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

        @Mapping(target = "id", ignore = true)
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
            return services == null ? List.of() :
                    services.stream()
                            .map(service -> service.getId().getValue())
                            .collect(Collectors.toList());
        }

        @Named("uuidsToServices")
        default List<Service> uuidsToServices(List<UUID> uuids) {
            // Implementar con repositorio real
            return List.of();
        }

        @Named("uuidToEmployeeId")
        default EmployeeId uuidToEmployeeId(UUID uuid) {
            return new EmployeeId(uuid);
        }

        // 👇 --- Conversores personalizados para Email y Rol ---

        @Named("stringToEmail")
        default Email stringToEmail(String value) {
            return value == null ? null : new Email(value);
        }

        @Named("emailToString")
        default String emailToString(Email email) {
            return email == null ? null : email.getValue();
        }

        @Named("stringToRol")
        default Rol stringToRol(String name) {
            return null;
        }

        @Named("rolToString")
        default String rolToString(Rol rol) {
            return rol == null ? null : rol.getClass().getSimpleName();
        }
    // 👇 Para convertir de String → Email
    default Email map(String value) {
        return value == null ? null : new Email(value);
    }

    // 👇 Para convertir de Email → String
    default String map(Email email) {
        return email == null ? null : email.getValue();
    }

    // 👇 Para convertir de Rol → String
    default String map(Rol rol) {
        return rol == null ? null : rol.getClass().getSimpleName();
    }

}


