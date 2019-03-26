package com.michalsadel.ecar.charge;

import com.michalsadel.ecar.price.dto.PriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;

class MapBasedCalculator implements ChargeCalculator {
    private static final Logger log = LoggerFactory.getLogger(MapBasedCalculator.class);
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    private final Map<Integer, BigDecimal> defaultPriceMapOfDay;
    private final ConversionService conversionService;

    public MapBasedCalculator(ConversionService conversionService) {
        this.conversionService = conversionService;
        defaultPriceMapOfDay = Collections.unmodifiableMap(
                IntStream.range(0, MINUTES_PER_DAY)
                        .boxed()
                        .collect(Collectors.toMap(Function.identity(), value -> ZERO)));
    }

    @Override
    public BigDecimal calculate(LocalDateTime startsAt, LocalDateTime finishesAt, List<PriceDto> prices) {
        log.info("Calculated using {}", getClass().getSimpleName());
        final Map<Integer, BigDecimal> priceMap = createPriceMap(prices);
        return LongStream.range(0, Duration.between(startsAt, finishesAt).toMinutes())
                .mapToObj(minute -> priceMap.get(minuteKey(startsAt, (int) minute)))
                .reduce(ZERO, BigDecimal::add);
    }

    private Integer getMinuteOfDay(LocalDateTime dateTime) {
        return conversionService.convert(dateTime, Integer.class);
    }

    private Integer minuteOfDayUntilPriceHasAnEffect(PriceDto price) {
        return conversionService.convert(price.getEffectedIn().getFinishesAt(), Integer.class);
    }

    private Integer minuteOfDaySincePriceHasAnEffect(PriceDto price) {
        return conversionService.convert(price.getEffectedIn().getStartsAt(), Integer.class);
    }

    private int minuteKey(LocalDateTime startsAt, int minute) {
        return (getMinuteOfDay(startsAt) + minute) % (MINUTES_PER_DAY - 1);
    }

    private Map<Integer, BigDecimal> createPriceMap(List<PriceDto> prices) {
        return Stream.concat(defaultPriceMapOfDay.entrySet().stream(), prices.stream()
                .sorted(Comparator.comparing(PriceDto::getDefaultInSystem).reversed())
                .map(price -> IntStream
                        .range(minuteOfDaySincePriceHasAnEffect(price), minuteOfDayUntilPriceHasAnEffect(price))
                        .mapToObj(minute -> new AbstractMap.SimpleEntry<>(minute, price.getPerMinute()))
                    )
                .flatMap(Function.identity()))
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (price1, price2) -> price2));
    }
}
