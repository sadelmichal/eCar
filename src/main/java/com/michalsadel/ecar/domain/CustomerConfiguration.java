package com.michalsadel.ecar.domain;

import org.springframework.context.annotation.*;

@Configuration
class CustomerConfiguration {
    @Bean
    ChargeCalculator chargeCalculator(PriceRepository priceRepository) {
        return new MapBasedCalculator(priceRepository);
    }

    @Bean
    CustomerEntryPoint customerService(CustomerRepository customerRepository, ChargeCalculator chargeCalculator) {
        return new CustomerEntryPoint(customerRepository, chargeCalculator);
    }
}
