package com.michalsadel.ecar.price;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PriceConfiguration {
    @Bean
    PriceFactory priceFactory() {
        return new DefaultPriceFactory();
    }

    @Bean
    PriceValidator priceValidator(PriceRepository priceRepository) {
        return PriceValidatorFactory.instanceOf(priceRepository);
    }

    @Bean
    PriceFacade priceService(PriceRepository priceRepository, PriceFactory priceFactory, PriceValidator priceValidator) {
        return new PriceFacade(priceRepository, priceFactory, priceValidator);
    }

}
