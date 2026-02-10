public class Main {
    public static void main(String[] args) {
        try {
            Race race = new Race();
            race.createRace();
            race.start();
            race.printScores();
            race.finishRace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
