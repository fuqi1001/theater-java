package com.jpmc.theater;

import com.jpmc.theater.utils.SequenceDiscount;
import com.jpmc.theater.utils.SpecialCode;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.jpmc.theater.utils.GlobalVariables.*;

public class Movie {

    private final String title;
    private final Duration runningTime;
    private final double ticketPrice;
    private final SpecialCode specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, SpecialCode specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getOrigTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        int seqOfDay = showing.getSequenceOfTheDay();
        LocalDateTime startTime = showing.getStartTime();
        double discount = getDiscount(seqOfDay, startTime);
        return ticketPrice - discount;
    }

    public double getDiscount(int showSequence, LocalDateTime startTime) {
        // for special discount
        double specialDiscount = 0;
        if (SpecialCode.SPECIAL == specialCode) {
            specialDiscount = ticketPrice * SpecialCode.SPECIAL.getDiscount();
        }

        // for sequenceDiscount
        double sequenceDiscount = 0;
        if (showSequence == SequenceDiscount.FIRST.getSequence()) {
            sequenceDiscount = SequenceDiscount.FIRST.getDiscount();
        } else if (showSequence == SequenceDiscount.SECOND.getSequence()) {
            sequenceDiscount = SequenceDiscount.SECOND.getDiscount();
        }

        // for time base discount
        int hour = startTime.getHour();
        int date = startTime.getDayOfMonth();
        double timeBaseDiscount = date == SPECIAL_DISCOUNT_DATE ? 1.0 : 0.0;
        timeBaseDiscount = (hour >= DISCOUNT_START_HOUR && hour <= DISCOUNT_END_HOUR) ? ticketPrice * HOUR_BASE_DISCOUNT_FACTOR : timeBaseDiscount;

        return Math.max(Math.max(specialDiscount, timeBaseDiscount), sequenceDiscount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, runningTime, ticketPrice, specialCode);
    }
}