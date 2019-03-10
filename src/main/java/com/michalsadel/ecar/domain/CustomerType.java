package com.michalsadel.ecar.domain;

import lombok.*;

enum CustomerType {
    DEFAULT(ChargeStrategy.defaultStrategy()), VIP(ChargeStrategy.vipStrategy());
    @Getter(AccessLevel.PACKAGE)
    private final ChargeStrategy chargeStrategy;

    CustomerType(ChargeStrategy chargeStrategy) {
        this.chargeStrategy = chargeStrategy;
    }

}
