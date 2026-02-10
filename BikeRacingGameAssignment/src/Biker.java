import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Biker implements Callable<Scoreboard> {

    private static int PTR = 0;
    private final String name;
    private final String color;

    private final CountDownLatch latch;

    public Biker(String name, CountDownLatch latch) {
        this.name = name;
        this.latch = latch;
        color = "\u001B[3" + ((PTR++ % 7) + 1) + "m";
    }

    private void print(String s) {
        System.out.println(color + s + "\u001B[0m");
    }

    public String toString() {
        return name;
    }

    @Override
    public Scoreboard call() throws InterruptedException {
        latch.await();
        LocalTime startTime = LocalTime.now();

        int distanceCovered = 0;
        int milestone = 100;

        while(distanceCovered < Race.getGoalDistanceMeters()) {
            int currentSpeed = (int) (50 + 150 * Math.random());

            distanceCovered += currentSpeed;
            if (distanceCovered > milestone && milestone < Race.getGoalDistanceMeters()) {
                print(name + " Crossed " + milestone + " meters");
                milestone += 100;
            }
            try {
                Thread.sleep(currentSpeed);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        return new Scoreboard(color, name, Race.getFinishRank(), startTime, LocalTime.now());
    }
}