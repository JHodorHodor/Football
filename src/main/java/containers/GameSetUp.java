package containers;

import logic.Observable;
import logic.State;
import logic.StateChanger;

public class GameSetUp {
    private final State state;
    private final StateChanger stateChanger;
    private final Observable observable;

    public GameSetUp(State state, StateChanger stateChanger, Observable observable){
        this.state = state;
        this.stateChanger = stateChanger;
        this.observable = observable;
    }

    public State getState() {
        return state;
    }

    public StateChanger getStateChanger() {
        return stateChanger;
    }

    public Observable getObservable() {
        return observable;
    }
}
