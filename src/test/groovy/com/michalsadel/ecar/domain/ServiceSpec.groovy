package com.michalsadel.ecar.domain

import com.michalsadel.ecar.dto.PriceDto
import com.michalsadel.ecar.dto.TimeRangeDto
import spock.lang.Specification

import java.time.LocalTime

class ServiceSpec extends Specification {
    ChargeService chargeService = new TestConfiguration().chagrgeService()


    private static LocalTime time(String time) {
        return LocalTime.parse(time)
    }

    static PriceDto createDefaultPrice(int price){
        PriceDto.builder()
                .perMinute(BigDecimal.valueOf(price))
                .build()
    }

    static PriceDto createPrice(String starts, String finishes){
        return PriceDto.builder()
            .perMinute(BigDecimal.ONE)
            .effectedIn(TimeRangeDto.builder()
                .startsAt(time(starts))
                .finishesAt(time(finishes))
                .build())
        .build()
    }
}