package com.michalsadel.ecar.charge;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static java.util.Objects.requireNonNull;

class LocalTimeToMinuteOfADayConverter implements Converter<LocalTime, Integer> {
    @Override
    public Integer convert(LocalTime source) {
        requireNonNull(source);
        return source.get(ChronoField.MINUTE_OF_DAY);
    }
}
