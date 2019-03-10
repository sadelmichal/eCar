package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.*;
import org.slf4j.*;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import static java.util.Objects.*;

class MapBasedCalculator implements ChargeCalculator {
    private static final Logger log = LoggerFactory.getLogger(MapBasedCalculator.class);

    private int minuteFromStart(PriceDto priceDto) {
        requireNonNull(priceDto);
        requireNonNull(priceDto.getEffectedIn());
        return priceDto.getEffectedIn().getStartsAt().get(ChronoField.MINUTE_OF_DAY);
    }

    private int minuteFromEnd(PriceDto priceDto) {
        requireNonNull(priceDto);
        requireNonNull(priceDto.getEffectedIn());
        return priceDto.getEffectedIn().getFinishesAt().get(ChronoField.MINUTE_OF_DAY);
    }

    private List<BigDecimal> createPriceMap(List<PriceDto> priceList) {
        final List<BigDecimal> mapping = new ArrayList<>();
        priceList.sort(Comparator.comparing(this::minuteFromStart));
        for (int i = 0; i < Duration.between(LocalTime.MIDNIGHT, LocalTime.MAX).toMinutes(); i++) {
            mapping.add(BigDecimal.ZERO);
        }
        for (PriceDto price : priceList) {
            for (int i = minuteFromStart(price); i < minuteFromEnd(price); i++) {
                mapping.set(i, price.getPerMinute());
            }
        }
        return Collections.unmodifiableList(mapping);
    }

    @Override
    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices) {
        final List<BigDecimal> priceMap = createPriceMap(prices);
        Duration duration = Duration.between(startsAt, finishesAt);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < duration.toMinutes(); i++) {
            sum = sum.add(priceMap.get((startsAt.get(ChronoField.MINUTE_OF_DAY) + i) % (int) ChronoField.MINUTE_OF_DAY.range().getMaximum()));
        }
        log.info("Calculated using {}", getClass().getSimpleName());
        return sum;
    }
}
