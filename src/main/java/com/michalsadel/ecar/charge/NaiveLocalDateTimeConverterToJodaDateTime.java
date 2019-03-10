package com.michalsadel.ecar.charge;

import org.joda.time.*;

class NaiveLocalDateTimeConverterToJodaDateTime {
    DateTime convert(java.time.LocalDateTime localDateTime) {
        return org.joda.time.LocalDateTime.parse(localDateTime.toString()).toDateTime();
    }
}
