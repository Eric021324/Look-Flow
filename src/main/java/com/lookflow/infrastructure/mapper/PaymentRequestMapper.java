package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.PaymentId;
import com.lookflow.infrastructure.dto.request.ProcessPaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {

    PaymentRequestMapper INSTANCE = Mappers.getMapper(PaymentRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentId", expression = "java(new PaymentId(UUID.randomUUID()))")
    @Mapping(target = "appointmentId", expression = "java(new AppointmentId(request.getAppointmentId()))")
    @Mapping(target = "customerId", expression = "java(new CustomerId(request.getCustomerId()))")
    @Mapping(target = "amount", expression = "java(new Money(request.getAmount()))")
    @Mapping(target = "date", source = "paymentDate")
    Payment toDomain(ProcessPaymentRequest request);
}
