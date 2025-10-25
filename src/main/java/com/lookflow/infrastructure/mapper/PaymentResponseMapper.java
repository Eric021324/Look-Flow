package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Payment;
import com.lookflow.infrastructure.dto.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PaymentResponseMapper {

    PaymentResponseMapper INSTANCE = Mappers.getMapper(PaymentResponseMapper.class);

    @Mapping(target = "id", expression = "java(payment.getId().getValue())")
    @Mapping(target = "appointmentId", expression = "java(payment.getAppointmentId().getValue())")
    @Mapping(target = "customerId", expression = "java(payment.getCustomerId().getValue())")
    @Mapping(target = "amount", expression = "java(payment.getAmount().getAmount())")
    @Mapping(target = "paymentDate", source = "date")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentResponse toResponse(Payment payment);
}
