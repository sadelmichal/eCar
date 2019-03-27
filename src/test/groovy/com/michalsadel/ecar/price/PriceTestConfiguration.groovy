package com.michalsadel.ecar.price

class PriceTestConfiguration {
    private final PriceRepository priceRepository

    PriceTestConfiguration() {
        this.priceRepository = new FakePriceRepository()
    }

    PriceFacade priceFacade() {
        return new PriceFacade(priceRepository, new DefaultPriceFactory(), PriceValidatorFactory.instanceOf(priceRepository))
    }
}
