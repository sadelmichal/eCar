package com.michalsadel.ecar.charge;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import static java.util.Objects.requireNonNull;

class LocalDateTimeToMinuteOfADayConverter implements Converter<LocalDateTime, Integer> {
    @Override
    public Integer convert(LocalDateTime source) {
        requireNonNull(source);
        return source.get(ChronoField.MINUTE_OF_DAY);
    }
}
