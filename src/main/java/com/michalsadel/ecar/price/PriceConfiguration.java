package com.michalsadel.ecar.price;

import org.springframework.context.annotation.*;

@Configuration
class PriceConfiguration {
    @Bean
    PriceFactory priceFactory() {
        return new DefaultPriceFactory();
    }

    @Bean
    PriceValidator priceValidator(PriceRepository priceRepository) {
        return new PriceValidator(priceRepository);
    }

    @Bean
    PriceFacade priceService(PriceRepository priceRepository, PriceFactory priceFactory, PriceValidator priceValidator) {
        return new PriceFacade(priceRepository, priceFactory, priceValidator);
    }

}
