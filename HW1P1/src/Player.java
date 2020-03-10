import java.util.Random;
import java.util.concurrent.Callable;

class Player implements Callable<Player> {
    public Move move;
    public int id;
    public int score;
    public int losses;
    public int wins;
    public int draws;
    public int gamesPlayed;

    public Player(int id){
        this.id = id;
        this.score = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.gamesPlayed = 0;
        }
    @Override
    public Player call() {
        Random random = new Random();
        this.move = Move.values()[random.nextInt(Main.MAX_MOVES)];
        return this;
    }
}
