import java.util.HashMap;

class Game implements Runnable {
    private HashMap<String,Integer> winMap;
    private Player one;
    private Player two;

    public Game(Player one, Player two, HashMap<String, Integer> winMap) {
        this.one = one;
        this.two = two;
        this.winMap = winMap;
    }

    @Override
    public void run() {
        String key = encodeGame();
        if (winMap.containsKey(key))
        {
            updateScore(winMap.get(key));
        }
        else
        {
            int winner = getWinner();
            updateScore(winner);
            winMap.put(key,winner);
        }
    }

    private String encodeGame()
    {
        return one.move.toString() + "x" + two.move.toString();
    }

    private int getWinner()
    {
        int winner = 0;

        if (one.move == Move.Paper)
        {
            if (two.move == Move.Scissors)
                winner = 2;
            else if (two.move == Move.Rock)
                winner = 1;
        }

        else if (one.move == Move.Scissors)
        {
            if (two.move == Move.Rock)
                winner = 2;
            else if (two.move == Move.Paper)
                winner = 1;
        }

        else if (one.move == Move.Rock)
        {
            if (two.move == Move.Paper)
                winner = 2;
            else if (two.move == Move.Scissors)
                winner = 1;
        }
        return winner;
    }

    private void updateScore(int winner)
    {
        one.gamesPlayed +=1;
        two.gamesPlayed +=1;
        if( winner == 1 )
        {
            one.score  += 1;
            one.wins += 1;
            two.score -= 1;
            two.losses += 1;
        }
        else if (winner == 2)
        {
            one.score -= 1;
            one.losses +=1;
            two.score += 1;
            two.wins +=1;
        }
        else{
            one.draws +=1;
            two.draws +=1;
        }
    }

}
