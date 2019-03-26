package com.michalsadel.ecar.charge;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

class NaiveLocalDateTimeConverterToJodaDateTime {
    DateTime convert(java.time.LocalDateTime localDateTime) {
        requireNonNull(localDateTime);
        return org.joda.time.LocalDateTime.parse(localDateTime.toString()).toDateTime();
    }

    DateTime convert(java.time.LocalTime localTime, java.time.LocalDate atDate) {
        requireNonNull(localTime);
        requireNonNull(atDate);
        return LocalDateTime.parse(localTime.atDate(atDate).toString()).toDateTime();
    }
}
