package com.jpmc.theater;

import com.jpmc.theater.utils.SpecialCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TheaterTest {

    private Theater theater;
    private Customer customer;
    private int audienceCount;

    @BeforeEach
    void setUp() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        theater = new Theater(provider);
        customer = new Customer("John", "123");
        Movie movie = new Movie("Spider-Man", Duration.ofMinutes(120), 10.0, SpecialCode.NONE);
        LocalDateTime startTime = LocalDateTime.now();
//        showing = new Showing(movie, 1, startTime);
        audienceCount = 5;
    }

    @Test
    public void testGetReservationById() {
        int targetSeq = 1;
        Optional<Showing> showing = theater.getShowingBySeq(targetSeq);
        assertTrue(showing.isPresent());


        Optional<Reservation> reservationResult = theater.reserve(customer, targetSeq, audienceCount);
        assertTrue(reservationResult.isPresent());
        Optional<Reservation> retrievedReservation = theater.getReservationById(reservationResult.get().id);

        assertTrue(retrievedReservation.isPresent());
        assertEquals(reservationResult.get(), retrievedReservation.get());
    }

    @Test
    public void testReserve() {
        int targetSeq = 1;
        Optional<Reservation> reservation = theater.reserve(customer, targetSeq, audienceCount);

        assertTrue(reservation.isPresent());
        assertEquals(customer, reservation.get().customer);
        assertEquals(audienceCount, reservation.get().audienceCount);
    }

    @Test
    public void testGetShowingBySeq() {
        int targetSeq = 1;
        Optional<Showing> retrievedShowing = theater.getShowingBySeq(targetSeq);
        assertTrue(retrievedShowing.isPresent());
    }

    @Test
    public void testTheaterInitialization() {
        assertNotNull(theater.provider);
        assertNotNull(theater.schedule);
        assertNotNull(theater.currDate);
        assertNotNull(theater.reservationMap);
        assertEquals(9, theater.schedule.size());
        assertEquals(0, theater.reservationMap.size());
    }

    @Test
    public void testGenerateStartTime() {
        LocalDateTime startTime = theater.generateStartTime(9, 0);
        assertNotNull(startTime);
        assertEquals(theater.currDate.getYear(), startTime.getYear());
        assertEquals(theater.currDate.getMonth(), startTime.getMonth());
        assertEquals(theater.currDate.getDayOfMonth(), startTime.getDayOfMonth());
        assertEquals(9, startTime.getHour());
        assertEquals(0, startTime.getMinute());
    }

    @Test
    public void testPrintSchedule() {
        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        theater.printSchedule();

        String expectedOutput = theater.currDate.toString() + System.lineSeparator() +
                "===================================================" + System.lineSeparator() +
                "1 : 2023-06-06T09:00 Turning Red (1 hour 25 minutes) $11.0\n" +
                "2 : 2023-06-06T11:00 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\n" +
                "3 : 2023-06-06T12:50 The Batman (1 hour 35 minutes) $9.0\n" +
                "4 : 2023-06-06T14:30 Turning Red (1 hour 25 minutes) $11.0\n" +
                "5 : 2023-06-06T16:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\n" +
                "6 : 2023-06-06T17:50 The Batman (1 hour 35 minutes) $9.0\n" +
                "7 : 2023-06-06T19:30 Turning Red (1 hour 25 minutes) $11.0\n" +
                "8 : 2023-06-06T21:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\n" +
                "9 : 2023-06-06T23:00 The Batman (1 hour 35 minutes) $9.0\n" +
                "===================================================" + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void testPrintScheduleJsonFmt() {
        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        theater.printScheduleJsonFmt();

        String expectedOutput = theater.currDate.toString() + System.lineSeparator() +
                "===================================================" + System.lineSeparator() +
                "{\n" +
                "  \"sequence\" : 1,\n" +
                "  \"starTime\" : \"2023-06-06T09:00\",\n" +
                "  \"title\" : \"Turning Red\",\n" +
                "  \"duration\" : \"(1 hour 25 minutes)\",\n" +
                "  \"fee\" : 11.0\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 2,\n" +
                "  \"starTime\" : \"2023-06-06T11:00\",\n" +
                "  \"title\" : \"Spider-Man: No Way Home\",\n" +
                "  \"duration\" : \"(1 hour 30 minutes)\",\n" +
                "  \"fee\" : 12.5\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 3,\n" +
                "  \"starTime\" : \"2023-06-06T12:50\",\n" +
                "  \"title\" : \"The Batman\",\n" +
                "  \"duration\" : \"(1 hour 35 minutes)\",\n" +
                "  \"fee\" : 9.0\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 4,\n" +
                "  \"starTime\" : \"2023-06-06T14:30\",\n" +
                "  \"title\" : \"Turning Red\",\n" +
                "  \"duration\" : \"(1 hour 25 minutes)\",\n" +
                "  \"fee\" : 11.0\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 5,\n" +
                "  \"starTime\" : \"2023-06-06T16:10\",\n" +
                "  \"title\" : \"Spider-Man: No Way Home\",\n" +
                "  \"duration\" : \"(1 hour 30 minutes)\",\n" +
                "  \"fee\" : 12.5\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 6,\n" +
                "  \"starTime\" : \"2023-06-06T17:50\",\n" +
                "  \"title\" : \"The Batman\",\n" +
                "  \"duration\" : \"(1 hour 35 minutes)\",\n" +
                "  \"fee\" : 9.0\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 7,\n" +
                "  \"starTime\" : \"2023-06-06T19:30\",\n" +
                "  \"title\" : \"Turning Red\",\n" +
                "  \"duration\" : \"(1 hour 25 minutes)\",\n" +
                "  \"fee\" : 11.0\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 8,\n" +
                "  \"starTime\" : \"2023-06-06T21:10\",\n" +
                "  \"title\" : \"Spider-Man: No Way Home\",\n" +
                "  \"duration\" : \"(1 hour 30 minutes)\",\n" +
                "  \"fee\" : 12.5\n" +
                "}\n" +
                "{\n" +
                "  \"sequence\" : 9,\n" +
                "  \"starTime\" : \"2023-06-06T23:00\",\n" +
                "  \"title\" : \"The Batman\",\n" +
                "  \"duration\" : \"(1 hour 35 minutes)\",\n" +
                "  \"fee\" : 9.0\n" +
                "}" + System.lineSeparator() +
                "===================================================" + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());

        // Reset System.out
        System.setOut(System.out);
    }
}

