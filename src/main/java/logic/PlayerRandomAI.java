package logic;

import containers.Move;

import java.util.Random;

public class PlayerRandomAI extends PlayerAIImpl{

    private Random random;

    public PlayerRandomAI(){
        super();
        random = new Random();
    }

    @Override
    public String getName() {
        return "RandomAI";
    }

    @Override
    Move getNextMove() {
        int i,j;
        Move m;
        do {
           i = random.nextInt(3) - 1;
           j = random.nextInt(3) - 1;
           m = new Move(i,j);
        } while (!this.getCalcGame().isLegal(m));
        return m;
    }
}
