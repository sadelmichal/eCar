package com.michalsadel.ecar.exceptions;

import java.time.*;

public class PriceOverlapsAnotherPriceException extends RuntimeException {
    public PriceOverlapsAnotherPriceException(LocalTime start, LocalTime finish) {
        super("Price provided overlaps price defined in the system between " + start + " and " + finish);
    }
}
