package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.exceptions.PriceOverlapsAnotherPriceException;

import java.util.List;
import java.util.function.Supplier;

class PriceOverlapValidator implements Validator {
    private final Supplier<List<Price>> priceDefinitionSupplier;

    PriceOverlapValidator(Supplier<List<Price>> priceDefinitionSupplier) {
        this.priceDefinitionSupplier = priceDefinitionSupplier;
    }

    @Override
    public void validate(Price incomingPrice) {
        priceDefinitionSupplier.get().stream()
                .filter(definedPrice -> definedPrice.isDefaultPrice() == incomingPrice.isDefaultPrice())
                .filter(definedPrice -> definedPrice.overlaps(incomingPrice))
                .findFirst()
                .ifPresent(definedPrice -> {
                    throw new PriceOverlapsAnotherPriceException(definedPrice.toString());
                });

    }
}
