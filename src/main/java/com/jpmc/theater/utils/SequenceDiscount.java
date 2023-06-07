package com.jpmc.theater.utils;

public enum SequenceDiscount {
    FIRST(1, 3),
    SECOND(2, 2);

    private final int sequence;
    private final double discount;

    SequenceDiscount(int sequence, double discount) {
        this.sequence = sequence;
        this.discount = discount;
    }

    public int getSequence() {
        return sequence;
    }

    public double getDiscount() {
        return discount;
    }
}

