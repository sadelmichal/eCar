package com.michalsadel.ecar.domain;

import org.springframework.transaction.annotation.*;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

class MapBasedCalculator implements ChargeCalculator {
    private final PriceRepository priceRepository;

    MapBasedCalculator(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    private List<BigDecimal> createPriceMap(List<Price> priceList) {
        final List<BigDecimal> mapping = new ArrayList<>();
        priceList.sort(Comparator.comparing(Price::getEffectSince));
        for (int i = 0; i < Duration.between(LocalTime.MIDNIGHT, LocalTime.MAX).toMinutes(); i++) {
            mapping.add(BigDecimal.ZERO);
        }
        for (Price price : priceList) {
            for (int i = price.getEffectSince().get(ChronoField.MINUTE_OF_DAY); i < price.getEffectUntil().get(ChronoField.MINUTE_OF_DAY); i++) {
                mapping.set(i, price.getPerMinute());
            }
        }
        return Collections.unmodifiableList(mapping);
    }

    @Override
    @Transactional
    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt) {
        final List<BigDecimal> priceMap = createPriceMap(priceRepository.findAll());
        Duration duration = Duration.between(startsAt, finishesAt);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < duration.toMinutes(); i++) {
            sum = sum.add(priceMap.get((startsAt.get(ChronoField.MINUTE_OF_DAY) + i) % (int) ChronoField.MINUTE_OF_DAY.range().getMaximum()));
        }
        return sum;
    }
}
