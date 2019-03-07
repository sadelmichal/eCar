package com.michalsadel.ecar.exceptions;

public class PriceInvalidTimeRangeException extends RuntimeException {
    public PriceInvalidTimeRangeException() {
        super("Price interval is invalid");
    }
}
