package com.michalsadel.ecar.charge;


import com.michalsadel.ecar.price.dto.PriceDto;
import com.michalsadel.ecar.price.dto.TimeRangeDto;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

class OverlapBasedCalculator implements ChargeCalculator {
    private static final Logger log = LoggerFactory.getLogger(OverlapBasedCalculator.class);
    private final NaiveLocalDateTimeConverterToJodaDateTime converter = new NaiveLocalDateTimeConverterToJodaDateTime();

    private Interval intervalAtDate(java.time.LocalDate date, PriceDto priceDto) {
        requireNonNull(priceDto);
        requireNonNull(priceDto.getEffectedIn());
        TimeRangeDto effectedIn = priceDto.getEffectedIn();
        return new Interval(converter.convert(effectedIn.getStartsAt(), date), converter.convert(effectedIn.getFinishesAt(), date));
    }

    private Optional<PriceDto> defaultPrice(final List<PriceDto> prices) {
        return prices.stream()
                .filter(PriceDto::getDefaultInSystem)
                .findFirst();
    }

    private BigDecimal reduceDefaultPrice(BigDecimal charge, PriceDto defaultPrice, int minutes) {
        return charge.subtract(defaultPrice.getPerMinute().multiply(BigDecimal.valueOf(minutes)));
    }

    private BigDecimal applyDefaultPrice(Duration chargeDuration, PriceDto defaultPrice) {
        long durationInMinutes = chargeDuration.toMinutes();
        return defaultPrice.getPerMinute().multiply(BigDecimal.valueOf(durationInMinutes));
    }

    private boolean durationInvalid(Duration duration) {
        return duration.isNegative() || duration.isZero();
    }

    @Override
    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices) {
        log.info("Calculated using {}", getClass().getSimpleName());
        BigDecimal charge = BigDecimal.ZERO;
        Duration chargeDuration = Duration.between(startsAt, finishesAt);
        if (durationInvalid(chargeDuration)) {
            return charge;
        }
        Optional<PriceDto> defaultPrice = defaultPrice(prices);
        if (defaultPrice.isPresent()) {
            charge = applyDefaultPrice(chargeDuration, defaultPrice.get());
        }
        List<PriceDto> nonDefaultPrices = prices.stream().filter(price -> !price.getDefaultInSystem()).collect(Collectors.toList());

        Interval chargeInterval = new Interval(converter.convert(startsAt), converter.convert(finishesAt));

        for (int i = 0; i < (Duration.between(startsAt, finishesAt).toDays() + 1) * nonDefaultPrices.size(); i++) {
            PriceDto price = nonDefaultPrices.get((i) % (nonDefaultPrices.size()));
            Interval priceInterval = intervalAtDate(startsAt.toLocalDate(), price);
            if (chargeInterval.overlaps(priceInterval)) {
                Interval intersectionInterval = chargeInterval.overlap(priceInterval);
                int intersectionMinutes = intersectionInterval.toPeriod().getMinutes();
                if (defaultPrice.isPresent()) {
                    charge = reduceDefaultPrice(charge, defaultPrice.get(), intersectionMinutes);
                }
                charge = charge.add(price.getPerMinute().multiply(BigDecimal.valueOf(intersectionMinutes)));
            }

        }
        return charge;
    }
}
