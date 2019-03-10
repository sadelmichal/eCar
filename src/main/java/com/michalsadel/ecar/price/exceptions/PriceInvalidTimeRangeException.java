package com.michalsadel.ecar.price.exceptions;

public class PriceInvalidTimeRangeException extends RuntimeException {
    public PriceInvalidTimeRangeException() {
        super("Price interval is invalid");
    }
}
