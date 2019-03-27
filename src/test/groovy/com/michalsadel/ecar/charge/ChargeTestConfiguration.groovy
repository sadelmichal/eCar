package com.michalsadel.ecar.charge

import org.springframework.core.convert.support.DefaultConversionService

class ChargeTestConfiguration {
    static ChargeFacade chargeFacadeWithOverlapCalculator() {
        return new ChargeFacade(new OverlapBasedCalculator())
    }

    static ChargeFacade chargeFacadeWithMapCalculator() {
        final DefaultConversionService conversionService = new DefaultConversionService()
        conversionService.addConverter(new LocalTimeToMinuteOfADayConverter())
        conversionService.addConverter(new LocalDateTimeToMinuteOfADayConverter())
        return new ChargeFacade(new MapBasedCalculator(conversionService))
    }

}
