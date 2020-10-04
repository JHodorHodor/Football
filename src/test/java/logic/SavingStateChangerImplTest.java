package logic;

import containers.Move;
import db.Database;
import exceptions.IllegalMoveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SavingStateChangerImplTest {
    @Test
    void makeMoveTest(){
        Database db = mock(Database.class);
        StateChanger stateChanger = mock(StateChanger.class);
        State state=mock(State.class);
        Move move = mock(Move.class);
        when(state.isLegal(move)).thenReturn(true);
        SavingStateChangerImpl savingStateChanger = new SavingStateChangerImpl(state,stateChanger,db);
        savingStateChanger.makeMove(move);
        verify(db).addMove(move);
        verify(stateChanger).makeMove(move);
        verifyNoMoreInteractions(db,stateChanger);
    }

    @Test
    void startGameTest(){
        Database db = mock(Database.class);
        StateChanger stateChanger = mock(StateChanger.class);
        State state=mock(State.class);
        SavingStateChangerImpl savingStateChanger = new SavingStateChangerImpl(state,stateChanger,db);
        savingStateChanger.startGame();
        verify(stateChanger).startGame();
    }

    @Test
    void makeIllegalMoveTest(){
        Database db = mock(Database.class);
        StateChanger stateChanger = mock(StateChanger.class);
        State state=mock(State.class);
        Move move = mock(Move.class);
        when(state.isLegal(move)).thenReturn(false);
        SavingStateChangerImpl savingStateChanger = new SavingStateChangerImpl(state,stateChanger,db);
        assertThrows(IllegalMoveException.class,()->savingStateChanger.makeMove(move));
        verifyNoMoreInteractions(db);
    }

}