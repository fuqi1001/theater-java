package com.jpmc.theater;

import com.jpmc.theater.utils.SpecialCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowingTest {

    LocalDateTime showStartTs = LocalDateTime.now();
    @Test
    public void testCalculateFee() {
        Movie movie = new Movie("Spider-Man", Duration.ofMinutes(120), 10.0, SpecialCode.NONE);
        Showing showing = new Showing(movie, 1, showStartTs);

        double fee = showing.calculateFee(5);
        assertEquals(fee, 35.0);
    }

    @Test
    public void testToDisplayable() {
        Movie movie = new Movie("Spider-Man", Duration.ofMinutes(120), 10.0, SpecialCode.NONE);
        Showing showing = new Showing(movie, 1, showStartTs);

        String displayable = showing.toDisplayable();
        String targetString = String.format("1 : %s Spider-Man (2 hours 0 minutes) $10.0", showStartTs);
        assertEquals(displayable, targetString);
    }

    @Test
    public void testToDisplayableJson() {
        Movie movie = new Movie("Spider-Man", Duration.ofMinutes(120), 10.0, SpecialCode.NONE);
        Showing showing = new Showing(movie, 1, showStartTs);

        String displayableJson = showing.toDisplayableJson();
        String targetJsonString = String.format("{\n" +
                "  \"sequence\" : 1,\n" +
                "  \"starTime\" : \"%s\",\n" +
                "  \"title\" : \"Spider-Man\",\n" +
                "  \"duration\" : \"(2 hours 0 minutes)\",\n" +
                "  \"fee\" : 10.0\n" +
                "}", showStartTs);
        assertEquals(displayableJson, targetJsonString);
    }
}
