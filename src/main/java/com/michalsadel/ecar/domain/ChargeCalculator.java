package com.michalsadel.ecar.domain;

import java.math.*;
import java.time.*;

public interface ChargeCalculator {
    BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt);
}
