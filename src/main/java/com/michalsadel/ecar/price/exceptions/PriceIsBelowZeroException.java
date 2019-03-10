package com.michalsadel.ecar.price.exceptions;

public class PriceIsBelowZeroException extends RuntimeException {
    public PriceIsBelowZeroException() {
        super("Protect our business, price per minute cannot be negative");
    }
}
