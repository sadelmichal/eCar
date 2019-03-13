package com.michalsadel.ecar.charge;

import org.springframework.core.convert.support.*;

public class ChargeTestConfiguration {

    public ChargeFacade chargeFacadeWithOverlapCalculator() {
        return new ChargeFacade(new OverlapBasedCalculator());
    }

    public ChargeFacade chargeFacadeWithMapCalculator() {
        final DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new LocalTimeToMinuteOfADayConverter());
        conversionService.addConverter(new LocalDateTimeToMinuteOfADayConverter());
        return new ChargeFacade(new MapBasedCalculator(conversionService));
    }
}
