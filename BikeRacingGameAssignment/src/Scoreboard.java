import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;

public record Scoreboard(String color, String name, int rank, LocalTime startTime, LocalTime endTime, Duration duration) {

    public Scoreboard(String color, String name, int rank, LocalTime startTime, LocalTime endTime) {
        this(color, name, rank, startTime, endTime, Duration.between(startTime, endTime));
    }

    @Override
    public String toString() {
        return color + name + " Finished " + Race.getFinishRank() + " | Started at " + startTime + ", Ended at " + endTime + ", Taken: " + durationFormatted();
    }

    public String durationFormatted() {
        return duration.getSeconds() + "s " + duration.toMillis() % 1000 + "ms";
    }

}
