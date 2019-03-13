package com.michalsadel.ecar.charge;

import org.springframework.core.convert.converter.*;

import java.time.*;
import java.time.temporal.*;

import static java.util.Objects.*;

class LocalTimeToMinuteOfADayConverter implements Converter<LocalTime, Integer> {
    @Override
    public Integer convert(LocalTime source) {
        requireNonNull(source);
        return source.get(ChronoField.MINUTE_OF_DAY);
    }
}
