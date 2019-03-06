package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import org.springframework.transaction.annotation.*;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import static java.util.Objects.*;


public class ChargeService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    ChargeService(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Transactional
    public void add(PriceDto priceDto) {
        requireNonNull(priceDto);
        final Price price = priceMapper.fromDto(priceDto);
        price.setEffectSince(Optional.ofNullable(price.getEffectSince()).orElse(LocalTime.MIDNIGHT));
        price.setEffectUntil(Optional.ofNullable(price.getEffectUntil()).orElse(LocalTime.MAX));
        priceRepository.save(price);
    }

    @Transactional
    public BigDecimal calculate(LocalDateTime startTime, LocalDateTime finishTime, Long customerId) {
        DailyPriceMappingCreator dailyPriceMappingCreator = new DailyPriceMappingCreator();

        List<Price> priceDefinitions = priceRepository.findAll();
        priceDefinitions.sort(Comparator.comparing(Price::getEffectSince));
        for (Price price : priceDefinitions) {
            dailyPriceMappingCreator.applyPrice(price);
        }

        List<BigDecimal> prices = dailyPriceMappingCreator.prices();


        Duration duration = Duration.between(startTime, finishTime);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < duration.toMinutes(); i++) {
            int minuteOfDay = startTime.get(ChronoField.MINUTE_OF_DAY) + i;
            BigDecimal priceInThisMinute = prices.get(minuteOfDay % 1439);
            sum = sum.add(priceInThisMinute);
        }

        return sum;
    }
}
