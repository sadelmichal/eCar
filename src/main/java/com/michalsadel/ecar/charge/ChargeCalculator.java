package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.*;

import java.math.*;
import java.time.*;
import java.util.*;

public interface ChargeCalculator {
    BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices);
}
