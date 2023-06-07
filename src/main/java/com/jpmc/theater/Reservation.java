package com.jpmc.theater;

import java.util.UUID;

public class Reservation {
    public final String id;
    public final Customer customer;
    public final Showing showing;
    public final int audienceCount;

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    public double totalFee() {
        return showing.calculateFee(audienceCount);
    }
}