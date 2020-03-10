import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

class Eliminate implements Callable<List<Future<Player>>> {

    List<Future<Player>> list;

    int lowestScore;

    int lowIndex;

    public Eliminate (List<Future<Player>> players){

        this.list = players;

    }


    @Override

    public List<Future<Player>> call() throws Exception {

        lowestScore = Integer.MAX_VALUE;

        lowIndex = 0;

        for (int i =0 ; i < this.list.size(); i++){
            if(lowestScore > list.get(i).get().score)
            {
                lowestScore = this.list.get(i).get().score;

                lowIndex = i;
            }
        }

        list.remove(lowIndex);
        return list;

    }

}
