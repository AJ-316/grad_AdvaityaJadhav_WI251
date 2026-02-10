import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Race {

    private static int RANK_PTR;
    private static float GOAL_DISTANCE_KM;
    private final CountDownLatch startLatch;
    private final ExecutorService executor;
    private List<Future<Scoreboard>> scoreboards;

    public Race() {
        startLatch = new CountDownLatch(3);
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void createRace() throws IOException, ExecutionException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Race Distance (KM): ");
        GOAL_DISTANCE_KM = Float.parseFloat(reader.readLine());

        System.out.println("Enter Bikers count (max " + Runtime.getRuntime().availableProcessors() +"): ");
        int totalBikers = Integer.parseInt(reader.readLine());
        scoreboards = new ArrayList<>();

        for(int i = 0; i < totalBikers; i++) {
            System.out.println("Enter Biker name: ");
            scoreboards.add(executor.submit(new Biker(reader.readLine(), startLatch)));
        }
    }

    public void finishRace() {
        executor.shutdown();
    }

    public void printScores() {
        if(scoreboards == null) {
            System.out.println("Race not created!");
            return;
        }
        try {
            for (Future<Scoreboard> scoreboard : scoreboards) {
                System.out.println(scoreboard.get());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void start() {
        RANK_PTR = 1;
        String[] countdown = {"Ready!", "Set!!", "GO!!!"};
        int counter = 0;
        while(counter < 3) {
            try {
                Thread.sleep(1000);
                startLatch.countDown();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(countdown[counter++]);
        }
    }

    public static int getGoalDistanceMeters() {
        return (int) (GOAL_DISTANCE_KM * 1000);
    }

    public static int getFinishRank() {
       return RANK_PTR++;
    }
}