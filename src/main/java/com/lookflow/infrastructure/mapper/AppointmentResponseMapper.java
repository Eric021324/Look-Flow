package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.infrastructure.dto.response.AppointmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper {

    AppointmentResponseMapper INSTANCE = Mappers.getMapper(AppointmentResponseMapper.class);

    @Mapping(target = "id", expression = "java(appointment.getId().getValue())")
    @Mapping(target = "customerId", expression = "java(appointment.getCustomerId().getValue())")
    @Mapping(target = "employeeId", expression = "java(appointment.getEmployeeId().getValue())")
    @Mapping(target = "serviceIds", expression = "java(mapServiceIds(appointment.getServiceIds()))")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "appointmentState", expression = "java(appointment.getAppointmentState().getClass().getSimpleName().replace(\"State\", \"\"))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AppointmentResponse toResponse(Appointment appointment);

    default List<UUID> mapServiceIds(List<com.lookflow.domain.model.valueobject.ServiceId> serviceIds) {
        return serviceIds.stream()
                .map(com.lookflow.domain.model.valueobject.ServiceId::getValue)
                .toList();
    }
}
