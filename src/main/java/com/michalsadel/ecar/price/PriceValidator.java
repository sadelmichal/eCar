package com.michalsadel.ecar.price;

import java.util.HashSet;
import java.util.Set;

class PriceValidator {
    private final Set<Validator> validators = new HashSet<>();

    void addValidator(Validator validator) {
        validators.add(validator);
    }

    void validate(Price incomingPrice) {
        validators.forEach(validator -> validator.validate(incomingPrice));
    }

}
