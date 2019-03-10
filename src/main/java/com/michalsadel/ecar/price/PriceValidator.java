package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.exceptions.*;

import java.util.*;

class PriceValidator {
    private final PriceRepository priceRepository;

    PriceValidator(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    void validate(Price price){
        priceRepository.findAll().stream()
                .filter(p -> p.isDefaultPrice() == price.isDefaultPrice())
                .filter(p -> p.overlaps(price))
                .findFirst()
                .ifPresent(p -> {
                    throw new PriceOverlapsAnotherPriceException(p.toString());
                });

        Optional.of(price)
                .filter(Price::isRangeValid)
                .orElseThrow(PriceInvalidTimeRangeException::new);
        Optional.of(price)
                .filter(Price::isPerMinuteValid)
                .orElseThrow(PriceIsBelowZeroException::new);


    }
}
