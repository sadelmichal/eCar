package com.michalsadel.ecar.domain;

import java.math.*;

import static java.util.Objects.requireNonNull;

class DiscountContext {
    private final DiscountStrategy discountStrategy;

    private DiscountContext(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    BigDecimal apply(BigDecimal charge) {
        requireNonNull(charge);
        return discountStrategy.discount(charge);
    }

    static DiscountContext from(CustomerType customerType) {
        return customerType == CustomerType.VIP
                ? new DiscountContext(new VipDiscount())
                : new DiscountContext(new StandardDiscount());
    }
}
