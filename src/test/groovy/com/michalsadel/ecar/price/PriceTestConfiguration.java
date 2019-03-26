package com.michalsadel.ecar.price;

public class PriceTestConfiguration {
    private final PriceRepository priceRepository;

    public PriceTestConfiguration() {
        this.priceRepository = new FakePriceRepository();
    }

    public PriceFacade priceFacade() {
        return new PriceFacade(priceRepository, new DefaultPriceFactory(), PriceValidatorFactory.instanceOf(priceRepository));
    }
}
