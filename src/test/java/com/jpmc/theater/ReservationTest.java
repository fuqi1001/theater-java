package com.jpmc.theater;

import com.jpmc.theater.utils.SpecialCode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    @Test
    public void totalFee() {
        var customer = new Customer("John Doe", "unused-id");
        var movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, SpecialCode.SPECIAL);
        var showing = new Showing(movie, 1, LocalDateTime.now());

        assertEquals(showing.calculateFee(3), 28.5);
        assertEquals(28.5, new Reservation(customer, showing, 3).totalFee());

        Reservation reservation = new Reservation(customer, showing, 3);
        assertEquals(28.5, reservation.totalFee());
    }
}
