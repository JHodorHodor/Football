package logic;

import containers.Move;
import exceptions.WrongPlayerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidatingStateChangerImplTest {


    @Test
    void wrongPlayerMakesMove(){
        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        ValidatingStateChanger validatingStateChanger = new ValidatingStateChangerImpl(state,stateChanger, player1);
        Move move = mock(Move.class);
        when(state.nextPlayer()).thenReturn(player2);
        assertThrows(WrongPlayerException.class,()->validatingStateChanger.makeMove(move));
        verifyNoMoreInteractions(stateChanger);
    }

    @Test
    void rightPlayerMakesMove(){
        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);
        Player player1 = mock(Player.class);
        ValidatingStateChanger validatingStateChanger = new ValidatingStateChangerImpl(state,stateChanger, player1);
        Move move = mock(Move.class);
        when(state.nextPlayer()).thenReturn(player1);
        assertDoesNotThrow(()->validatingStateChanger.makeMove(move));
        verify(stateChanger).makeMove(move);
    }

    @Test
    void startGameTest(){
        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);
        Player player1 = mock(Player.class);
        ValidatingStateChanger validatingStateChanger = new ValidatingStateChangerImpl(state,stateChanger, player1);
        validatingStateChanger.startGame();
        verify(stateChanger).startGame();
        verifyNoMoreInteractions(stateChanger);
    }


}