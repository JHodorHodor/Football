package logic;

import containers.Edge;
import containers.Move;
import containers.Vertex;

public class PlayerHuman implements Player{

    private String name;
    private State state;
    private StateChanger stateChanger;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void initialize(State state, StateChanger stateChanger) {
        this.state = state;
        this.stateChanger = stateChanger;
        state.attach(this);
    }

    @Override
    public void start() {
        //empty
    }

    @Override
    public void update(Edge edge) {
        //empty
    }

    public void makeMove(Vertex vertex){
        stateChanger.makeMove(new Move(state.getBall(), vertex));
    }
}
