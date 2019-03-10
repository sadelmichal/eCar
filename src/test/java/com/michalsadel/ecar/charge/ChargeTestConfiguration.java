package com.michalsadel.ecar.charge;

public class ChargeTestConfiguration {

    public ChargeFacade chargeFacadeWithOverlapCalculator() {
        return new ChargeFacade(new OverlapBasedCalculator());
    }

    public ChargeFacade chargeFacadeWithMapCalculator() {
        return new ChargeFacade(new MapBasedCalculator());
    }
}
