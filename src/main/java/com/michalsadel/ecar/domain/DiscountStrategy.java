package com.michalsadel.ecar.domain;

import java.math.*;

interface DiscountStrategy {
    default BigDecimal discount(BigDecimal charge){
        return charge;
    }
}
