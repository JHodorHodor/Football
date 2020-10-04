package ui;

import containers.Edge;

public interface GameView {
    void initialize(GamePresenter gamePresenter);
    void open();
    void addEdge(Edge edge);
    void setCurrentPlayer(String name);
    void displayWinner(String winner);
    void close();

    TaskRunner taskRunner();
}
