package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.exceptions.PriceIsBelowZeroException;

import java.util.Optional;

class PriceBelowZeroValidator implements Validator {
    @Override
    public void validate(Price incomingPrice) {
        Optional.of(incomingPrice)
                .filter(Price::isPerMinuteValid)
                .orElseThrow(PriceIsBelowZeroException::new);
    }
}
