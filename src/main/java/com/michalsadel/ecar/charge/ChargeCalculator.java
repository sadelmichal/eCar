package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.PriceDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ChargeCalculator {
    BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices);
}
