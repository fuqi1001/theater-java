package com.jpmc.theater;

import java.time.LocalDate;

public class LocalDateProvider {

    /**
     * initialization-on-demand holder idiom
     */
    private static final class InstanceHolder {
        static final LocalDateProvider instance = new LocalDateProvider();
    }

    /**
     * @return make sure to return singleton instance
     */
    public static LocalDateProvider singleton() {
        return InstanceHolder.instance;
    }

    public LocalDate currentDate() {
            return LocalDate.now();
    }
}
