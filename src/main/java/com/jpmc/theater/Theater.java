package com.jpmc.theater;

import com.jpmc.theater.utils.SpecialCode;

import javax.sound.sampled.ReverbType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Theater {

    LocalDateProvider provider;
    public Map<Integer, Showing> schedule;
    public LocalDate currDate;
    public Map<String, Reservation> reservationMap;

    public Theater(LocalDateProvider provider) {
        this.provider = provider;
        this.schedule = new HashMap<>();
        this.currDate = provider.currentDate();
        this.reservationMap = new HashMap<>();

        // init movies
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, SpecialCode.SPECIAL);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, SpecialCode.NONE);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, SpecialCode.NONE);

        // init schedule map
        schedule.put(1, new Showing(turningRed, 1, generateStartTime(9, 0)));
        schedule.put(2, new Showing(spiderMan, 2, generateStartTime(11, 0)));
        schedule.put(3, new Showing(theBatMan, 3, generateStartTime(12, 50)));
        schedule.put(4, new Showing(turningRed, 4, generateStartTime(14, 30)));
        schedule.put(5, new Showing(spiderMan, 5, generateStartTime(16, 10)));
        schedule.put(6, new Showing(theBatMan, 6, generateStartTime(17, 50)));
        schedule.put(7, new Showing(turningRed, 7, generateStartTime(19, 30)));
        schedule.put(8, new Showing(spiderMan, 8, generateStartTime(21, 10)));
        schedule.put(9, new Showing(theBatMan, 9, generateStartTime(23, 0)));
    }

    public Optional<Reservation> getReservationById(String id) {
        if (reservationMap.containsKey(id)) {
            return Optional.of(reservationMap.get(id));
        } else return Optional.empty();
    }

    public LocalDateTime generateStartTime(int hour, int minute) {
        return LocalDateTime.of(this.currDate, LocalTime.of(hour, minute));
    }

    public Optional<Reservation> reserve(Customer customer, int sequence, int audienceCount) {
        if (schedule.containsKey(sequence)) {
            Showing showing = schedule.get(sequence);
            Reservation rev = new Reservation(customer, showing, audienceCount);
            reservationMap.put(rev.id, rev);
            return Optional.of(rev);
        } else return Optional.empty();
    }

    public void printSchedule() {
        System.out.println(currDate);
        System.out.println("===================================================");
        schedule.forEach((key, s) -> System.out.println(s.toDisplayable()));
        System.out.println("===================================================");
    }

    public void printScheduleJsonFmt() {
        System.out.println(currDate);
        System.out.println("===================================================");

        schedule.forEach((key, s) -> System.out.println(s.toDisplayableJson()));
        System.out.println("===================================================");
    }


    public Optional<Showing> getShowingBySeq(int seq) {
        if (schedule.containsKey(seq)) {
            return Optional.of(schedule.get(seq));
        } else return Optional.empty();
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
        theater.printScheduleJsonFmt();

        theater.getShowingBySeq(2).ifPresent(showing -> System.out.println(showing.calculateFee(1)));
    }
}
