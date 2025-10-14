package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.ServiceId;
import com.lookflow.domain.model.valueobject.appointmentstate.AppointmentState;
import com.lookflow.infrastructure.dto.AppointmentDto;
import com.lookflow.infrastructure.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "employeeId", source = "employeeId.value")
    @Mapping(target = "serviceIds", source = "serviceIds", qualifiedByName = "serviceIdsToUuids")
    @Mapping(target = "appointmentState", source = "appointmentState", qualifiedByName = "appointmentStateToString")
    AppointmentDto toDto(Appointment appointment);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToAppointmentId")
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = "uuidToCustomerId")
    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = "uuidToEmployeeId")
    @Mapping(target = "serviceIds", source = "serviceIds", qualifiedByName = "uuidsToServiceIds")
    @Mapping(target = "appointmentState", source = "appointmentState", qualifiedByName = "stringToAppointmentState")
    Appointment toDomain(AppointmentDto appointmentDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "serviceIds", source = "serviceIds")
    @Mapping(target = "appointmentState", source = "appointmentState")
    AppointmentDto entityToDto(AppointmentEntity appointmentEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "serviceIds", source = "serviceIds")
    @Mapping(target = "appointmentState", source = "appointmentState")
    AppointmentEntity dtoToEntity(AppointmentDto appointmentDto);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToAppointmentId")
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = "uuidToCustomerId")
    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = "uuidToEmployeeId")
    @Mapping(target = "serviceIds", source = "serviceIds", qualifiedByName = "uuidsToServiceIds")
    @Mapping(target = "appointmentState", source = "appointmentState", qualifiedByName = "stringToAppointmentState")
    Appointment entityToDomain(AppointmentEntity appointmentEntity);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "employeeId", source = "employeeId.value")
    @Mapping(target = "serviceIds", source = "serviceIds", qualifiedByName = "serviceIdsToUuids")
    @Mapping(target = "appointmentState", source = "appointmentState", qualifiedByName = "appointmentStateToString")
    AppointmentEntity domainToEntity(Appointment appointment);

    @Named("serviceIdsToUuids")
    default List<UUID> serviceIdsToUuids(List<ServiceId> serviceIds) {
        return serviceIds.stream()
                .map(ServiceId::getValue)
                .collect(Collectors.toList());
    }

    @Named("uuidsToServiceIds")
    default List<ServiceId> uuidsToServiceIds(List<UUID> uuids) {
        return uuids.stream()
                .map(ServiceId::new)
                .collect(Collectors.toList());
    }

    @Named("appointmentStateToString")
    default String appointmentStateToString(AppointmentState appointmentState) {
        return appointmentState.getClass().getSimpleName().replace("State", "");
    }

    @Named("stringToAppointmentState")
    default AppointmentState stringToAppointmentState(String appointmentState) {
        // This would need to be implemented based on your state pattern
        // For now, returning null as it should be handled by the domain
        return null;
    }

    @Named("uuidToAppointmentId")
    default AppointmentId uuidToAppointmentId(UUID uuid) {
        return new AppointmentId(uuid);
    }

    @Named("uuidToCustomerId")
    default CustomerId uuidToCustomerId(UUID uuid) {
        return new CustomerId(uuid);
    }

    @Named("uuidToEmployeeId")
    default EmployeeId uuidToEmployeeId(UUID uuid) {
        return new EmployeeId(uuid);
    }
}
