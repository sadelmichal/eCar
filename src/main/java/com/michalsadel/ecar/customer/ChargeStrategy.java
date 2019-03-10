package com.michalsadel.ecar.customer;

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
