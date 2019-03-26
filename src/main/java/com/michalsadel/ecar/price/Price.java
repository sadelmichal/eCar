package com.michalsadel.ecar.price;

import com.michalsadel.ecar.Entity;
import com.michalsadel.ecar.price.dto.PriceDto;
import com.michalsadel.ecar.price.dto.TimeRangeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Price extends Entity<Long> {
    private BigDecimal perMinute;
    private LocalTime effectSince;
    private LocalTime effectUntil;

    boolean isDefaultPrice() {
        return effectSince.equals(LocalTime.MIDNIGHT) && effectUntil.truncatedTo(ChronoUnit.SECONDS).equals(LocalTime.MAX.truncatedTo(ChronoUnit.SECONDS));
    }

    boolean overlaps(Price price) {
        requireNonNull(price);
        return effectSince.isBefore(price.effectUntil) && effectUntil.isAfter(price.effectSince);
    }

    boolean isRangeValid() {
        return effectUntil.isAfter(effectSince);
    }

    boolean isPerMinuteValid() {
        return perMinute.signum() != -1;
    }

    PriceDto toDto() {
        return PriceDto.builder()
                .perMinute(perMinute)
                .effectedIn(TimeRangeDto.builder()
                        .startsAt(effectSince)
                        .finishesAt(effectUntil)
                        .build())
                .defaultInSystem(isDefaultPrice())
                .build();
    }

}




