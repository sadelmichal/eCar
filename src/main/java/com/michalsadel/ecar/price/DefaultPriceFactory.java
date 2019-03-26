package com.michalsadel.ecar.price;

import com.michalsadel.ecar.price.dto.PriceDto;
import com.michalsadel.ecar.price.dto.TimeRangeDto;

import java.time.LocalTime;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class DefaultPriceFactory implements PriceFactory {
    @Override
    public Price from(PriceDto priceDto) {
        requireNonNull(priceDto);
        TimeRangeDto effectedIn = Optional.ofNullable(priceDto.getEffectedIn())
                .orElse(TimeRangeDto.builder()
                        .startsAt(LocalTime.MIDNIGHT)
                        .finishesAt(LocalTime.MAX)
                        .build());
        return Price.builder()
                .perMinute(priceDto.getPerMinute())
                .effectSince(Optional.ofNullable(effectedIn.getStartsAt()).orElse(LocalTime.MIDNIGHT))
                .effectUntil(Optional.ofNullable(effectedIn.getFinishesAt()).orElse(LocalTime.MAX))
                .build();
    }
}
