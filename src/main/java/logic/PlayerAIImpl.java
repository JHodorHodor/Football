package logic;

import containers.Edge;
import containers.Move;
import containers.Vertex;

import java.util.*;

public abstract class PlayerAIImpl implements PlayerAI {

    private List<Vertex> goals;
    private List<Vertex> ownGoals;
    private State state;
    private StateChanger stateChanger;
    private CalcGame calcGame;
    private boolean started = false;
    private Thread calculator;



    @Override
    public void initialize(State state, StateChanger stateChanger) {
        this.state = state;
        this.stateChanger = stateChanger;
        calculator = null;
        state.attach(this);
        calcGame = new CalcGame(state.getPlayer1(),state.getPlayer2(),new ArrayList<>());
    }

    @Override
    public void start() {
        started=true;
    }

    @Override
    public abstract String getName();

    abstract Move getNextMove();

    @Override
    public void setName(String name) {}

    @Override
    public void update(Edge e) {
        if(e!=null) {
            Move m;
            Vertex v;
            Vertex ball=calcGame.getBall();
            if(!e.getV1().equals(ball)) {
                v=e.getV1();
            } else {
                v=e.getV2();
            }
            m=new Move(v.getX()-ball.getX(),v.getY()-ball.getY());
            calcGame.makeMove(m);
            calcGame.endTurn();
        }
        if(started && state.nextPlayer() == this){
            if(started)
                play();
        }

    }

    @Override
    public void calibrate(List<Vertex> goals, List<Vertex> ownGoals) {
        this.goals=goals;
        this.ownGoals=ownGoals;
    }

    private void play(){
        if(Thread.currentThread()==calculator){
            makeMove();
        }
        else {
            calculator = new Thread(this::makeMove);
            calculator.start();
        }
    }

    void makeMove(){
        stateChanger.makeMove(getNextMove());
    }

    List<Vertex> getGoals() {
        return goals;
    }

    List<Vertex> getOwnGoals() {
        return ownGoals;
    }

    CalcGame getCalcGame() {
        return calcGame;
    }

    boolean isStarted() {
        return started;
    }
    void setCalculator(Thread calculator) {
        this.calculator = calculator;
    }
}
