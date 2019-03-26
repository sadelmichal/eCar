package com.michalsadel.ecar.charge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.togglz.core.manager.FeatureManager;
import org.togglz.spring.proxy.FeatureProxyFactoryBean;

@Configuration
class ChargeConfiguration {
    @Bean
    MapBasedCalculator mapBasedChargeCalculator(ConversionService conversionService) {
        return new MapBasedCalculator(conversionService);
    }

    @Bean
    OverlapBasedCalculator overlapBasedCalculator() {
        return new OverlapBasedCalculator();
    }

    @Bean
    ChargeCalculator chargeCalculator(FeatureProxyFactoryBean featureProxyFactoryBean, FeatureManager featureManager) throws Exception {
        return (ChargeCalculator) featureProxyFactoryBean.getObject();
    }

    @Bean
    FeatureProxyFactoryBean chargeCalculatorFactoryBean(OverlapBasedCalculator overlapBasedCalculator, MapBasedCalculator mapBasedCalculator) {
        FeatureProxyFactoryBean bean = new FeatureProxyFactoryBean();
        bean.setActive(overlapBasedCalculator);
        bean.setInactive(mapBasedCalculator);
        bean.setFeature(OverlapBasedCalculationFeature.OVERLAP_BASED_CALCULATION_FEATURE.name());
        return bean;
    }

    @Bean
    ChargeFacade chargeFacade(ChargeCalculator chargeCalculator) {
        return new ChargeFacade(chargeCalculator);
    }


}
