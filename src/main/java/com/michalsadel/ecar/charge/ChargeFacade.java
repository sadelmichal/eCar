package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.*;

import java.math.*;
import java.time.*;
import java.util.*;

public class ChargeFacade {
    private final ChargeCalculator chargeCalculator;

    public ChargeFacade(ChargeCalculator chargeCalculator) {
        this.chargeCalculator = chargeCalculator;
    }

    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices) {
        return chargeCalculator.calculate(startsAt, finishesAt, prices);
    }
}
