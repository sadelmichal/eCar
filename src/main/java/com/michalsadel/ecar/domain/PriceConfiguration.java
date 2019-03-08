package com.michalsadel.ecar.domain;

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
    PriceEntryPoint priceService(PriceRepository priceRepository, PriceFactory priceFactory, PriceValidator priceValidator) {
        return new PriceEntryPoint(priceRepository, priceFactory, priceValidator);
    }

}
