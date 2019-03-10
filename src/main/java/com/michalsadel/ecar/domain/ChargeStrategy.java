package com.michalsadel.ecar.domain;

import java.math.*;

@FunctionalInterface
interface ChargeStrategy {
    BigDecimal apply(BigDecimal charge);

    static ChargeStrategy defaultStrategy() {
        return charge -> charge;
    }

    static ChargeStrategy vipStrategy() {
        return charge -> charge.subtract(charge.multiply(BigDecimal.valueOf(0.1)));
    }
}
