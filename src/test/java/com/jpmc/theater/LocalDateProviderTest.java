package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class LocalDateProviderTest {
    @Test
    public void testSingleton() {
        LocalDateProvider provider1 = LocalDateProvider.singleton();
        LocalDateProvider provider2 = LocalDateProvider.singleton();

        assertSame(provider1, provider2);
    }

    @Test
    public void testCurrentDate() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        LocalDate currentDate = provider.currentDate();
        LocalDate today = LocalDate.now();

        assertEquals(today, currentDate);
    }
}
