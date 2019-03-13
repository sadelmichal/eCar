package com.michalsadel.ecar.charge;

import org.springframework.core.convert.converter.*;

import java.time.*;
import java.time.temporal.*;

import static java.util.Objects.*;

class LocalDateTimeToMinuteOfADayConverter implements Converter<LocalDateTime, Integer> {
    @Override
    public Integer convert(LocalDateTime source) {
        requireNonNull(source);
        return source.get(ChronoField.MINUTE_OF_DAY);
    }
}
