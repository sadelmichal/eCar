package com.michalsadel.ecar.customer;

import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

enum CustomerType {
    DEFAULT(CustomerType::normalPrice), VIP(CustomerType::tenPercentDiscount);
    @Getter(AccessLevel.PACKAGE)
    private final Function<BigDecimal, BigDecimal> discount;

    CustomerType(Function<BigDecimal, BigDecimal> discount) {
        this.discount = discount;
    }

    private static BigDecimal tenPercentDiscount(BigDecimal charge) {
        return charge.subtract(charge.multiply(BigDecimal.TEN).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
    }

    private static BigDecimal normalPrice(BigDecimal charge) {
        return charge;
    }
}
