package com.michalsadel.ecar.domain;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

class DailyPriceMappingCreator {
    private final List<BigDecimal> mapping = new ArrayList<>();

    DailyPriceMappingCreator() {
        for (int i = 0; i < Duration.between(LocalTime.MIDNIGHT, LocalTime.MAX).toMinutes(); i++) {
            mapping.add(BigDecimal.ZERO);
        }
    }

    void applyPrice(Price price) {
        for (int i = price.getEffectSince().get(ChronoField.MINUTE_OF_DAY); i < price.getEffectUntil().get(ChronoField.MINUTE_OF_DAY); i++) {
            mapping.set(i, price.getPerMinute());
        }
    }

    List<BigDecimal> prices() {
        return Collections.unmodifiableList(mapping);
    }
}
