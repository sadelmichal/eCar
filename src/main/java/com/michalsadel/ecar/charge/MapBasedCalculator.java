package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.*;
import org.slf4j.*;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.math.BigDecimal.*;
import static java.util.Objects.*;

class MapBasedCalculator implements ChargeCalculator {
    private static final Logger log = LoggerFactory.getLogger(MapBasedCalculator.class);
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    private final Map<Integer, BigDecimal> defaultPriceMapOfDay;

    public MapBasedCalculator() {
        defaultPriceMapOfDay = Collections.unmodifiableMap(
                IntStream.range(0, MINUTES_PER_DAY)
                        .boxed()
                        .collect(Collectors.toMap(Function.identity(), value -> ZERO)));
    }

    private int getMinuteOfDay(LocalTime time) {
        requireNonNull(time);
        return time.get(ChronoField.MINUTE_OF_DAY);
    }

    private int getMinuteOfDay(LocalDateTime time) {
        requireNonNull(time);
        return time.get(ChronoField.MINUTE_OF_DAY);
    }

    private Map<Integer, BigDecimal> createPriceMap(List<PriceDto> prices) {
        final Map<Integer, BigDecimal> priceMapOfADay = prices.stream()
                .sorted(Comparator.comparing(PriceDto::getDefaultInSystem).reversed())
                .map(price -> IntStream
                        .range(
                                getMinuteOfDay(price.getEffectedIn().getStartsAt()),
                                getMinuteOfDay(price.getEffectedIn().getFinishesAt()))
                        .mapToObj(minute -> new AbstractMap.SimpleImmutableEntry<>(minute, price.getPerMinute()))
                )
                .flatMap(Function.identity())
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue, (price1, price2) -> price2));

        return Stream.concat(defaultPriceMapOfDay.entrySet().stream(), priceMapOfADay.entrySet().stream())
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (price1, price2) -> price2));

    }

    @Override
    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices) {
        log.info("Calculated using {}", getClass().getSimpleName());
        final Map<Integer, BigDecimal> priceMap = createPriceMap(prices);
        return LongStream.range(0, Duration.between(startsAt, finishesAt).toMinutes())
                .mapToObj(minute -> priceMap.get((getMinuteOfDay(startsAt) + (int) minute) % (MINUTES_PER_DAY - 1)))
                .reduce(ZERO, BigDecimal::add);
    }
}
