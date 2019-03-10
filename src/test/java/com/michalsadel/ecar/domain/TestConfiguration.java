package com.michalsadel.ecar.domain;

import org.springframework.util.*;

import java.lang.reflect.*;

class TestConfiguration {
    private final PriceRepository priceRepository;
    private final ChargeCalculator chargeCalculator;

    TestConfiguration(Class<ChargeCalculator> chargeCalculatorClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.priceRepository = new FakePriceRepository();
        Constructor<ChargeCalculator> chargeCalculatorConstructor = ReflectionUtils.accessibleConstructor(chargeCalculatorClass, PriceRepository.class);
        this.chargeCalculator = chargeCalculatorConstructor.newInstance(this.priceRepository);
    }

    CustomerEntryPoint customerService() {
        return new CustomerEntryPoint(new FakeCustomerRepository(), chargeCalculator);
    }

    PriceEntryPoint priceService() {
        return new PriceEntryPoint(priceRepository, new DefaultPriceFactory(), new PriceValidator(priceRepository));
    }
}
