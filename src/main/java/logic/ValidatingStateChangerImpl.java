package logic;

import containers.Move;
import exceptions.IllegalMoveException;
import exceptions.WrongPlayerException;

public class ValidatingStateChangerImpl implements ValidatingStateChanger{

    private State state;
    private StateChanger stateChanger;
    private Player player;

    ValidatingStateChangerImpl(State state, StateChanger stateChanger, Player player) {
        this.state = state;
        this.stateChanger = stateChanger;
        this.player = player;
    }

    @Override
    public void makeMove(Move move) throws IllegalMoveException, WrongPlayerException {
        if(state.nextPlayer() != this.player) throw new WrongPlayerException("Wrong player! Await your turn");
        stateChanger.makeMove(move);
    }

    @Override
    public void startGame() {
        stateChanger.startGame();
    }


}
