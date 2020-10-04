package logic;

import containers.Move;
import exceptions.IllegalMoveException;

public interface StateChanger {
    void makeMove(Move move) throws IllegalMoveException;
    void startGame();
}
