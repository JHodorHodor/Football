package logic;

import containers.Edge;
import containers.Move;
import containers.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerAIImplTest {

    @Test
    void initializeTest(){
        PlayerAIImpl playerAI = new PlayerAIImpl() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            Move getNextMove() {
                return null;
            }
        };

        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);

        playerAI.initialize(state, stateChanger);

        verify(state).attach(playerAI);
    }

    @Test
    void startTest(){
        PlayerAIImpl playerAI = new PlayerAIImpl() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            Move getNextMove() {
                return null;
            }
        };

        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);

        assertFalse(playerAI.isStarted());

        playerAI.initialize(state, stateChanger);
        assertFalse(playerAI.isStarted());

        playerAI.start();
        assertTrue(playerAI.isStarted());
    }

    @Test
    void updateTest(){
        PlayerAIImpl playerAI = new PlayerAIImpl() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            Move getNextMove() {
                return new Move(-1,-1);
            }
        };

        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);
        when(state.nextPlayer()).thenReturn(playerAI);
        playerAI.initialize(state, stateChanger);
        playerAI.start();

        Vertex start = new Vertex((Game.RIGHT_BARRIER + Game.LEFT_BARRIER) / 2, (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 );
        Vertex other = new Vertex((Game.RIGHT_BARRIER + Game.LEFT_BARRIER) / 2 + 1, (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 + 1);
        Edge e = new Edge(start, other);

        playerAI.setCalculator(Thread.currentThread());
        playerAI.update(e);
        verify(stateChanger).makeMove(new Move(-1,-1));
    }

    @Test
    void updateTest2(){
        PlayerAIImpl playerAI = new PlayerAIImpl() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            Move getNextMove() {
                return new Move(-1,-1);
            }
        };

        State state = mock(State.class);
        StateChanger stateChanger = mock(StateChanger.class);
        when(state.nextPlayer()).thenReturn(playerAI);
        playerAI.initialize(state, stateChanger);
        playerAI.start();

        Vertex start = new Vertex((Game.RIGHT_BARRIER + Game.LEFT_BARRIER) / 2, (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 );
        Vertex other = new Vertex((Game.RIGHT_BARRIER + Game.LEFT_BARRIER) / 2 + 1, (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 + 1);
        Edge e = new Edge(other, start);

        playerAI.setCalculator(Thread.currentThread());
        playerAI.update(e);
        verify(stateChanger).makeMove(new Move(-1,-1));
    }

    @Test
    void calibrateTest(){
        PlayerAIImpl playerAI = new PlayerAIImpl() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            Move getNextMove() {
                return null;
            }
        };

        ArrayList<Vertex> l1 = new ArrayList<>();
        ArrayList<Vertex> l2 = new ArrayList<>();

        playerAI.calibrate(l1, l2);
        assertEquals(playerAI.getGoals(), l1);
        assertEquals(playerAI.getOwnGoals(), l2);
    }
}
