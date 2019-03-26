package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.exceptions.PriceInvalidTimeRangeException;

import java.util.Optional;

class PriceRangeValidator implements Validator {

    @Override
    public void validate(Price incomingPrice) {
        Optional.of(incomingPrice)
                .filter(Price::isRangeValid)
                .orElseThrow(PriceInvalidTimeRangeException::new);

    }
}
