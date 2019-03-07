package com.michalsadel.ecar.domain

import com.michalsadel.ecar.dto.DateTimeRangeDto
import com.michalsadel.ecar.dto.PriceDto
import com.michalsadel.ecar.dto.TimeRangeDto

import java.time.LocalDateTime
import java.time.LocalTime

trait ServiceSpec {
    ChargeService chargeService = new TestConfiguration().chagrgeService()

    private static LocalTime time(String time) {
        return LocalTime.parse(time)
    }

    static LocalDateTime dateTime(String dateTime) {
        return LocalDateTime.parse(dateTime)
    }

    static PriceDto createDefaultPrice(BigDecimal price = 1.0){
        PriceDto.builder()
                .perMinute(price)
                .build()
    }

    static PriceDto createPrice(String starts, String finishes, BigDecimal price = 1.0){
        return PriceDto.builder()
            .perMinute(price)
            .effectedIn(TimeRangeDto.builder()
                .startsAt(time(starts))
                .finishesAt(time(finishes))
                .build())
        .build()
    }

    static DateTimeRangeDto createDuration(String starts, String finishes){
        return DateTimeRangeDto.builder()
            .start(dateTime(starts))
            .finish(dateTime(finishes))
        .build()
    }
}