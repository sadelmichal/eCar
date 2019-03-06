package com.michalsadel.ecar.domain;

import java.math.*;

class NoDiscount implements DiscountStrategy {

    @Override
    public BigDecimal discount(BigDecimal chargePrice) {
        return chargePrice;
    }
}
