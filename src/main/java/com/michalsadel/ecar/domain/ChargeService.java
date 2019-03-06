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
    private final PriceFactory priceFactory;


    ChargeService(PriceRepository priceRepository, PriceFactory priceFactory) {
        this.priceRepository = priceRepository;
        this.priceFactory = priceFactory;
    }

    @Transactional
    public void add(PriceDto priceDto) {
        requireNonNull(priceDto);
        Price price = priceFactory.from(priceDto);
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
