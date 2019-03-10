package com.michalsadel.ecar.domain;


import org.joda.time.*;
import org.slf4j.*;

import java.math.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

class LinearBasedCalculator implements ChargeCalculator {
    private static final Logger log = LoggerFactory.getLogger(LinearBasedCalculator.class);
    private final PriceRepository priceRepository;
    private final NaiveLocalDateTimeConverterToJodaDateTime converter;

    LinearBasedCalculator(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
        this.converter = new NaiveLocalDateTimeConverterToJodaDateTime();
    }

    private Optional<Price> defaultPrice(final List<Price> prices) {
        return prices.stream()
                .filter(Price::isDefaultPrice)
                .findFirst();
    }

    private BigDecimal reduceDefaultPrice(BigDecimal charge, Price defaultPrice, int minutes) {
        return charge.subtract(defaultPrice.getPerMinute().multiply(BigDecimal.valueOf(minutes)));
    }

    private BigDecimal applyDefaultPrice(Duration chargeDuration, Price defaultPrice) {
        long durationInMinutes = chargeDuration.toMinutes();
        return defaultPrice.getPerMinute().multiply(BigDecimal.valueOf(durationInMinutes));
    }

    private boolean durationInvalid(Duration duration) {
        return duration.isNegative() || duration.isZero();
    }

    @Override
    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt) {
        BigDecimal charge = BigDecimal.ZERO;
        List<Price> prices = priceRepository.findAll();

        Duration chargeDuration = Duration.between(startsAt, finishesAt);
        if (durationInvalid(chargeDuration)) {
            return charge;
        }
        Optional<Price> defaultPrice = defaultPrice(prices);
        if (defaultPrice.isPresent()) {
            charge = applyDefaultPrice(chargeDuration, defaultPrice.get());
        }
        prices = prices.stream().filter(price -> !price.isDefaultPrice()).collect(Collectors.toList());

        Interval chargeInterval = new Interval(converter.convert(startsAt), converter.convert(finishesAt));

        for (int i = 0; i < (Duration.between(startsAt, finishesAt).toDays() + 1) * prices.size(); i++) {
            Price price = prices.get((i) % (prices.size()));
            Interval priceInterval = price.intervalAtDate(startsAt.toLocalDate());
            if (chargeInterval.overlaps(priceInterval)) {
                Interval intersectionInterval = chargeInterval.overlap(priceInterval);
                int intersectionMinutes = intersectionInterval.toPeriod().getMinutes();
                if (defaultPrice.isPresent()) {
                    charge = reduceDefaultPrice(charge, defaultPrice.get(), intersectionMinutes);
                }
                charge = charge.add(price.getPerMinute().multiply(BigDecimal.valueOf(intersectionMinutes)));
            }

        }
        log.info("Calculated using {}", getClass().getSimpleName());
        return charge;
    }
}
