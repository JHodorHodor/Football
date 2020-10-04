package logic;

import containers.Move;
import containers.Vertex;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class PlayerSimpleAI extends PlayerAIImpl{
    //implements a simple heuristic: randomize turn a few (difficulty dependent) times, and return the one closest to enemy goal


    private int difficulty;
    private Random random;
    private Queue<Move> queue;


    PlayerSimpleAI(){
        this.queue = new LinkedList<>();
        this.random = new Random();
    }

    @Override
    public abstract String getName();


    @Override
    Move getNextMove() {
        if(!queue.isEmpty()){
            return queue.remove();
        }
        boolean firstIter=true;
        Vertex best_curr=new Vertex(0,0); //the value here doesn't matter
        for(int i=0; i< difficulty*30;i++){
            Queue<Move> curr = simulateTurn();
            if(!this.getCalcGame().hasLegalMoves() || this.getOwnGoals().contains(this.getCalcGame().getBall())){
                if(firstIter){ //we want to save some move even if it is loosing
                    queue=curr;
                }
            }
            else if(firstIter || compare(this.getCalcGame().getBall(),best_curr)){
                best_curr=this.getCalcGame().getBall();
                queue=curr;
                firstIter=false;
            }
            this.getCalcGame().clearTurn();
        }
        return queue.remove();
    }

    private int distance(Vertex v1, Vertex v2){
        return Math.max(Math.abs(v1.getX()-v2.getX()),Math.abs(v1.getY()-v2.getY()));
    }

    private boolean compare(Vertex v1, Vertex v2){ //true if v1 is closer to goal than v2
        int dist1=Integer.MAX_VALUE, dist2=Integer.MAX_VALUE;
        for(Vertex g: this.getGoals()){
            dist1=Math.min(dist1,distance(v1,g));
            dist2=Math.min(dist2,distance(v2,g));
        }
        return dist1<dist2;
    }

    private Move randomMove(){
        Move ret;
        do{
            ret = new Move(random.nextInt(3)-1,random.nextInt(3)-1);
        } while (ret.getUpDown()==0 && ret.getLeftRight()==0);
        return ret;
    }

    private Queue<Move> simulateTurn(){
        LinkedList<Move> ret = new LinkedList<>();
        while (true){
            if(!this.getCalcGame().hasLegalMoves()) break;
            Move m = randomMove();
            if(!this.getCalcGame().isLegal(m)) continue;
            ret.add(m);
            this.getCalcGame().makeMove(m);
            if(this.getOwnGoals().contains(this.getCalcGame().getBall())) break;
            if(this.getGoals().contains(this.getCalcGame().getBall())) break;
            if (this.getCalcGame().turnChanged()) break;
        }
        return ret;
    }

    void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
