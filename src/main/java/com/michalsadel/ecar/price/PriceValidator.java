package com.michalsadel.ecar.price;

import java.util.HashSet;
import java.util.Set;

class PriceValidator implements Validator {
    private final Set<Validator> validators = new HashSet<>();

    void addValidator(Validator validator) {
        validators.add(validator);
    }

    @Override
    public void validate(Price incomingPrice) {
        validators.forEach(validator -> validator.validate(incomingPrice));
    }
}
