package com.michalsadel.ecar.domain;

import java.math.*;
import java.time.*;

interface ChargeCalculator {
    BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt);
}
