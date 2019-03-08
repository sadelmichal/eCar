package com.michalsadel.ecar.domain;

class TestConfiguration {
    private final PriceRepository priceRepository;

    TestConfiguration() {
        this.priceRepository = new FakePriceRepository();
    }

    CustomerEntryPoint customerService() {
        return new CustomerEntryPoint(new FakeCustomerRepository(), new MapBasedCalculator(priceRepository));
    }

    PriceEntryPoint priceService() {
        return new PriceEntryPoint(priceRepository, new DefaultPriceFactory(), new PriceValidator(priceRepository));
    }
}
