package logic;

public interface Player extends Observer {

    String getName();
    void setName(String name);
    void initialize(State state, StateChanger stateChanger);
    void start();

}
