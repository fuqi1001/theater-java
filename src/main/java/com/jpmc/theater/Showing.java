package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.jpmc.theater.utils.TimeUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

public class Showing {
    private final Movie movie;
    private final int sequenceOfTheDay;
    private final LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.getOrigTicketPrice();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public double calculateFee(int audienceCount) {
        return movie.calculateTicketPrice(this) * audienceCount;
    }

    public String toDisplayable() {
        return String.format(
            "%s : %s %s %s $%s",
            this.sequenceOfTheDay,
            this.getStartTime(),
            this.movie.getTitle(),
            TimeUtil.humanReadableFormatByDuration(this.movie.getRunningTime()),
            this.getMovieFee()
        );
    }

    public String toDisplayableJson() {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter writer = new StringWriter();
        try {
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
            jsonGenerator.useDefaultPrettyPrinter();
            jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("sequence", this.sequenceOfTheDay);
            jsonGenerator.writeStringField("starTime", this.getStartTime().toString());
            jsonGenerator.writeStringField("title", this.movie.getTitle());
            jsonGenerator.writeStringField("duration", TimeUtil.humanReadableFormatByDuration(this.movie.getRunningTime()));
            jsonGenerator.writeNumberField("fee", this.getMovieFee());
            jsonGenerator.close();

            return writer.toString();
        } catch (IOException exception) {
            System.out.printf("... Fail to generate json string for showing %s%n", this.toDisplayable());
            return "";
        }
    }

}
