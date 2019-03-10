package com.michalsadel.ecar.exceptions;

public class PriceOverlapsAnotherPriceException extends RuntimeException {
    public PriceOverlapsAnotherPriceException(String price) {
        super("Price provided overlaps price defined in the system : " + price);
    }
}
