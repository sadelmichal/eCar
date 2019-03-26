package com.michalsadel.ecar.price;

class PriceValidatorFactory {
    private PriceValidatorFactory() {
    }

    static PriceValidator instanceOf(PriceRepository priceRepository) {
        PriceValidator priceValidator = new PriceValidator();
        priceValidator.addValidator(new PriceOverlapValidator(priceRepository::findAll));
        priceValidator.addValidator(new PriceRangeValidator());
        priceValidator.addValidator(new PriceBelowZeroValidator());
        return priceValidator;
    }
}
