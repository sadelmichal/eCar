package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.PriceDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ChargeFacade {
    private final ChargeCalculator chargeCalculator;

    public ChargeFacade(ChargeCalculator chargeCalculator) {
        this.chargeCalculator = chargeCalculator;
    }

    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices) {
        return chargeCalculator.calculate(startsAt, finishesAt, prices);
    }
}
