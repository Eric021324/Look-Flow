package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.PaymentId;
import com.lookflow.infrastructure.dto.PaymentDto;
import com.lookflow.infrastructure.persistence.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "appointmentId", source = "appointmentId.value")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "amount", source = "amount.amount")
    PaymentDto toDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentId", source = "appointmentId", qualifiedByName = "uuidToAppointmentId")
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = "uuidToCustomerId")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "bigDecimalToMoney")
    Payment toDomain(PaymentDto paymentDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "appointmentId", source = "appointmentId")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "amount", source = "amount")
    PaymentDto entityToDto(PaymentEntity paymentEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "appointmentId", source = "appointmentId")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "amount", source = "amount")
    PaymentEntity dtoToEntity(PaymentDto paymentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentId", source = "appointmentId", qualifiedByName = "uuidToAppointmentId")
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = "uuidToCustomerId")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "bigDecimalToMoney")
    Payment entityToDomain(PaymentEntity paymentEntity);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "appointmentId", source = "appointmentId.value")
    @Mapping(target = "customerId", source = "customerId.value")
    @Mapping(target = "amount", source = "amount.amount")
    PaymentEntity domainToEntity(Payment payment);

    @Named("uuidToPaymentId")
    default PaymentId uuidToPaymentId(UUID uuid) {
        return PaymentId.of(uuid);
    }

    @Named("uuidToAppointmentId")
    default AppointmentId uuidToAppointmentId(UUID uuid) {
        return new AppointmentId(uuid);
    }

    @Named("uuidToCustomerId")
    default CustomerId uuidToCustomerId(UUID uuid) {
        return new CustomerId(uuid);
    }

    @Named("bigDecimalToMoney")
    default Money bigDecimalToMoney(BigDecimal amount) {
        return new Money(amount);
    }
}
