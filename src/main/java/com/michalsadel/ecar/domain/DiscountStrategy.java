package com.michalsadel.ecar.domain;

import java.math.*;

interface DiscountStrategy {
    BigDecimal discount(BigDecimal chargePrice);

}
