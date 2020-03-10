import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static final int MAX_MOVES = 3;
    static final int MAX_THREADS = 4;


    static final int MAX_PLAYERS = 200;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);
        List<Future<Player>> players = new ArrayList<>(MAX_PLAYERS);
        HashMap<String,Integer> winMap = new HashMap<>();

        // initializes the players
        createPlayers(executor,players);

        // play game until there is a winner
        startGame(executor,players,winMap);

        long end = System.currentTimeMillis();
        float total = (end - start)/1000F;
        System.out.println("Total execution time: " + total + " seconds.");

    }

    public static void createPlayers(ThreadPoolExecutor executor, List<Future<Player>> players)
    {
        for (int i = 0; i < MAX_PLAYERS; i++)
        {
            Player player = new Player(i+1);
            Future<Player> future = executor.submit(player);
            players.add(future);
        }
    }


    public static void startGame(ThreadPoolExecutor executor, List<Future<Player>> players, HashMap<String,Integer> winMap) throws InterruptedException {

        while(players.size()>1)

        {
            makeMoves(executor,players);

            playGames(executor, players, winMap);

            try {
                Future<List<Future<Player>>> future = executor.submit( new Eliminate(players));
                players = future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        declareWinner(players);

        executor.shutdown();

    }
    public static void makeMoves(ThreadPoolExecutor executor, List<Future<Player>> players) throws InterruptedException{
        for (int j = 0; j < players.size(); j++){
            try {
                Future<Player> p = players.get(j);
                executor.submit(p.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    public static void playGames(ThreadPoolExecutor executor, List<Future<Player>> players, HashMap<String,Integer> winMap) {

        for (int i = 0; i < players.size()-1; i++)
        {
            Future<Player> future1 = players.get(i);
            Player player1;
            for (int j = i+1; j < players.size(); j++)
                {
                    Future<Player> future2 = players.get(j);
                    Player player2;
                    try {
                        player1 = future1.get();
                        player2 = future2.get();
                        Game game = new Game(player1, player2,winMap);
                        executor.submit(game);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    public static void declareWinner(List<Future<Player>> players) {
        System.out.print("Winner: Player ");
        try {
            Player winner = players.get(0).get();
            System.out.println(winner.id + " with " + winner.wins + " wins, " + winner.losses + " losses and " + winner.draws + " draws.");
            System.out.println("Total " + winner.gamesPlayed + " games played.");
            System.out.println("Player score: " + winner.score);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}



