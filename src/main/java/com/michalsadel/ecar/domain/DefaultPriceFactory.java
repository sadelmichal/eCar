package com.michalsadel.ecar.domain;

import com.michalsadel.ecar.dto.*;

import java.time.*;
import java.util.*;

import static java.util.Objects.*;

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
