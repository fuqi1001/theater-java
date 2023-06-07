package com.jpmc.theater;

import com.jpmc.theater.utils.SequenceDiscount;
import com.jpmc.theater.utils.SpecialCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.jpmc.theater.utils.GlobalVariables.HOUR_BASE_DISCOUNT_FACTOR;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    @Test
    public void specialMovieWith50PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, SpecialCode.SPECIAL);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        assertEquals(10, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    public void testGetDiscount() {
        // Test case 1: No special code, sequence discount, or time-based discount
        Movie movie1 = new Movie("Movie 1", null, 10.0, SpecialCode.NONE);
        int sequence1 = SequenceDiscount.FIRST.getSequence();
        LocalDateTime startTime1 = LocalDateTime.now();
        double discount1 = movie1.getDiscount(sequence1, startTime1);
        assertEquals(SequenceDiscount.FIRST.getDiscount(), discount1);

        // Test case 2: Special code discount
        Movie movie2 = new Movie("Movie 2", null, 10.0, SpecialCode.SPECIAL);
        int sequence2 = SequenceDiscount.SECOND.getSequence();
        LocalDateTime startTime2 = LocalDateTime.now();
        double discount2 = movie2.getDiscount(sequence2, startTime2);
        double expectedDiscount2 = movie2.getOrigTicketPrice() * SpecialCode.SPECIAL.getDiscount();
        assertEquals(expectedDiscount2, discount2);

        // Test case 3: Sequence discount
        Movie movie3 = new Movie("Movie 3", null, 10.0, SpecialCode.NONE);
        int sequence3 = SequenceDiscount.SECOND.getSequence();
        LocalDateTime startTime3 = LocalDateTime.now();
        double discount3 = movie3.getDiscount(sequence3, startTime3);
        double expectedDiscount3 = SequenceDiscount.SECOND.getDiscount();
        assertEquals(expectedDiscount3, discount3);

        // Test case 4: Time-based discount
        Movie movie4 = new Movie("Movie 4", null, 10.0, SpecialCode.NONE);
        int sequence4 = 4;
        // Within discount hours
        LocalDateTime startTime4 = LocalDateTime.now().withHour(15);
        double discount4 = movie4.getDiscount(sequence4, startTime4);
        double expectedDiscount4 = movie4.getOrigTicketPrice() * HOUR_BASE_DISCOUNT_FACTOR;
        assertEquals(expectedDiscount4, discount4);

        Movie movie5 = new Movie("Movie 5", null, 10.0, SpecialCode.NONE);
        int sequence5 = 5;
        LocalDateTime startTime5 = LocalDateTime.now().withHour(10); // Outside discount hours
        double discount5 = movie5.getDiscount(sequence5, startTime5);
        assertEquals(0.0, discount5);

        // Test case 6: Combination of discounts
        Movie movie6 = new Movie("Movie 6", null, 10.0, SpecialCode.SPECIAL);
        int sequence6 = 6;
        // Within discount hours
        LocalDateTime startTime6 = LocalDateTime.now().withHour(13);
        double discount6 = movie6.getDiscount(sequence6, startTime6);
        double expectedDiscount6 = Math.max(
                movie6.getOrigTicketPrice() * SpecialCode.SPECIAL.getDiscount(),
                movie6.getOrigTicketPrice() * HOUR_BASE_DISCOUNT_FACTOR
        );
        assertEquals(expectedDiscount6, discount6);
    }

    @Test
    void equalsShouldReturnTrueWhenMoviesAreEqual() {
        // Arrange
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(150), 12.5, SpecialCode.SPECIAL);
        Movie movie2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(150), 12.5, SpecialCode.SPECIAL);

        // Act
        boolean result = movie1.equals(movie2);

        // Assert
        assertTrue(result);
    }

    @Test
    void equalsShouldReturnFalseWhenMoviesAreNotEqual() {
        // Arrange
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(150), 12.5, SpecialCode.SPECIAL);
        Movie movie2 = new Movie("The Batman", Duration.ofMinutes(120), 10.0, SpecialCode.NONE);

        // Act
        boolean result = movie1.equals(movie2);

        // Assert
        assertFalse(result);
    }

    @Test
    void equalsShouldReturnFalseWhenObjectIsNotMovie() {
        // Arrange
        Movie movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(150), 12.5, SpecialCode.SPECIAL);
        String otherObject = "This is not a Movie object";

        // Act
        boolean result = movie.equals(otherObject);

        // Assert
        assertFalse(result);
    }

    @Test
    void equalsShouldReturnFalseWhenMovieIsNull() {
        // Arrange
        Movie movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(150), 12.5, SpecialCode.SPECIAL);

        // Act
        boolean result = movie.equals(null);

        // Assert
        assertFalse(result);
    }

}
