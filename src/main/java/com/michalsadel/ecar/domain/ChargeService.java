package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;
import com.michalsadel.ecar.exceptions.*;
import org.springframework.transaction.annotation.*;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import static java.util.Objects.*;


public class ChargeService {
    private final PriceRepository priceRepository;
    private final CustomerRepository customerRepository;
    private final PriceFactory priceFactory;


    ChargeService(PriceRepository priceRepository, CustomerRepository customerRepository, PriceFactory priceFactory) {
        this.priceRepository = priceRepository;
        this.customerRepository = customerRepository;
        this.priceFactory = priceFactory;
    }

    @Transactional
    public PriceDto add(PriceDto priceDto) {
        requireNonNull(priceDto);
        Price price = priceFactory.from(priceDto);

        priceRepository.findAll().stream()
                .filter(p -> p.isDefaultPrice() == price.isDefaultPrice())
                .filter(p -> p.overlaps(price))
                .findFirst()
                .ifPresent(p -> {
                    throw new PriceOverlapsAnotherPriceException(p.getEffectSince(), p.getEffectUntil());
                });

        Optional.of(price)
                .filter(Price::isValid)
                .orElseThrow(PriceInvalidTimeRangeException::new);

        return priceFactory.from(priceRepository.save(price));
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
        Customer customer = customerRepository.findById(customerId);
        return discountContext(customer).discount(sum);
    }

    private DiscountStrategy discountContext(Customer customer) {
        return customer.customerType() == CustomerType.VIP ? new TenPercentDiscount() : new NoDiscount();
    }
}
