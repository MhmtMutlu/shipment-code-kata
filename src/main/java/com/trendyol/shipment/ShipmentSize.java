package com.trendyol.shipment;

public enum ShipmentSize {

    SMALL,
    MEDIUM,
    LARGE,
    X_LARGE;

    public ShipmentSize next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public boolean isNotLast() {
        return this.ordinal() != values().length - 1;
    }
}
