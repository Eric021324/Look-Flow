package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.ServiceId;
import com.lookflow.infrastructure.dto.request.CreateAppointmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AppointmentRequestMapper {

    AppointmentRequestMapper INSTANCE = Mappers.getMapper(AppointmentRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", expression = "java(new CustomerId(request.getCustomerId()))")
    @Mapping(target = "employeeId", expression = "java(new EmployeeId(request.getEmployeeId()))")
    @Mapping(target = "serviceIds", expression = "java(mapServiceIds(request.getServiceIds()))")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "appointmentState", ignore = true)
    Appointment toDomain(CreateAppointmentRequest request);

    default List<ServiceId> mapServiceIds(List<UUID> serviceIds) {
        return serviceIds.stream()
                .map(ServiceId::new)
                .toList();
    }
}
