package logic;

import exceptions.IllegalMoveException;
import containers.Move;
import db.Database;

public class SavingStateChangerImpl implements SavingStateChanger{

    private StateChanger stateChanger;
    private Database database;
    private State state;


    SavingStateChangerImpl(State state, StateChanger stateChanger, Database database) {
        this.stateChanger = stateChanger;
        this.database = database;
        this.state=state;
    }

    @Override
    public void makeMove(Move move) throws IllegalMoveException {
        if(state.isLegal(move)){
            database.addMove(move);
            stateChanger.makeMove(move);
        }
        else {
            throw new IllegalMoveException();
        }
    }

    @Override
    public void startGame() {
        stateChanger.startGame();
    }
}
