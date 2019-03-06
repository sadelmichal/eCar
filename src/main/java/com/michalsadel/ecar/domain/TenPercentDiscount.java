package com.michalsadel.ecar.domain;

import java.math.*;

class TenPercentDiscount implements DiscountStrategy {

    @Override
    public BigDecimal discount(BigDecimal chargePrice) {
        return chargePrice.subtract(chargePrice.multiply(BigDecimal.valueOf(0.1)));
    }
}
