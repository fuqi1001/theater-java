package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheaterTests {
    @Test
    void testReservationFee() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Optional<Reservation> reservation = theater.reserve(john, 2, 4);
        reservation.ifPresent(value -> assertEquals(value.totalFee(), 40));
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void printMovieScheduleJson() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printScheduleJsonFmt();
    }

    @Test
    void testReservation() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Optional<Reservation> rev = theater.reserve(john, 2, 4);
        if (rev.isPresent()) {
            var fixedRev = rev.get();
            var revFromTheater = theater.getReservationById(fixedRev.id);
            revFromTheater.ifPresent(reservation -> assertEquals(reservation, fixedRev));
        }

        Optional<Reservation> failedRev = theater.reserve(john, 1001, 4);
        assertTrue(failedRev.isEmpty(), "failedRev should be empty");
    }

}
