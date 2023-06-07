package com.jpmc.theater.utils;

public enum SpecialCode {
    NONE(0, 0),
    SPECIAL(1, 0.2);

    private final int code;
    private final double discount;

    SpecialCode(int code, double discount) {
        this.code = code;
        this.discount = discount;
    }

    public int getCode() {
        return code;
    }

    public double getDiscount() {
        return discount;
    }
}
