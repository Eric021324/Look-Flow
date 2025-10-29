package com.lookflow.domain.model.valueobject;

import java.util.UUID;

public class PaymentId extends BaseId<UUID>{
    public PaymentId(UUID value) {
        super(value);
    }

    public static PaymentId of(UUID value) {
        return new PaymentId(value);
    }
}
