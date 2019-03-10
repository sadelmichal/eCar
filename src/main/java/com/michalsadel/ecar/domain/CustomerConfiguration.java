package com.michalsadel.ecar.domain;

import org.springframework.context.annotation.*;
import org.togglz.core.manager.*;
import org.togglz.spring.proxy.*;

@Configuration
class CustomerConfiguration {

    @Bean
    MapBasedCalculator mapBasedChargeCalculator(PriceRepository priceRepository) {
        return new MapBasedCalculator(priceRepository);
    }

    @Bean
    LinearBasedCalculator linearBasedCalculator(PriceRepository priceRepository) {
        return new LinearBasedCalculator(priceRepository);
    }

    @Bean
    ChargeCalculator chargeCalculator(FeatureProxyFactoryBean featureProxyFactoryBean, FeatureManager featureManager) throws Exception {
        return (ChargeCalculator) featureProxyFactoryBean.getObject();
    }

    @Bean
    FeatureProxyFactoryBean chargeCalculatorFactoryBean(LinearBasedCalculator linearBasedCalculator, MapBasedCalculator mapBasedCalculator) {
        FeatureProxyFactoryBean bean = new FeatureProxyFactoryBean();
        bean.setActive(linearBasedCalculator);
        bean.setInactive(mapBasedCalculator);
        bean.setFeature(LinearCalculationAlgorithmFeature.LINEAR_BASED_CALCULATION.name());
        return bean;
    }


    @Bean
    CustomerEntryPoint customerService(CustomerRepository customerRepository, ChargeCalculator chargeCalculator) {
        return new CustomerEntryPoint(customerRepository, chargeCalculator);
    }
}
