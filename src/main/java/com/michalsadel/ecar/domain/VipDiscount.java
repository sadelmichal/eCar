package com.michalsadel.ecar.domain;

import java.math.*;

class VipDiscount implements DiscountStrategy {
    private final static BigDecimal DISCOUNT = BigDecimal.valueOf(0.1);

    @Override
    public BigDecimal discount(BigDecimal charge) {
        return charge.subtract(charge.multiply(DISCOUNT));
    }
}
