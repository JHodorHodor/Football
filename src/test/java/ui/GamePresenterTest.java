package ui;

import containers.Edge;
import containers.Vertex;
import logic.Player;
import logic.PlayerHuman;
import logic.PlayerSimpleAI;
import logic.State;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GamePresenterTest {

    @Test
    void openTest(){
        GameView gameView = mock(GameView.class);
        when(gameView.taskRunner()).thenReturn(new SimpleTaskRunner());
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.open();
        verify(gameView).open();

    }



    @Test
    void initTest() {
        GameView gameView = mock(GameView.class);
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        State state = mock(State.class);
        Player player1 = mock(PlayerHuman.class);
        Player player2 = mock(PlayerSimpleAI.class);

        when(state.getPlayer1()).thenReturn(player1);
        when(state.getPlayer2()).thenReturn(player2);

        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.initialize(state);

        verify(state).getPlayer1();
        verify(state).getPlayer2();
    }

    @Test
    void performMoveTest() {
        GameView gameView = mock(GameView.class);
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        State state = mock(State.class);
        Vertex vertex = new Vertex(0, 0);
        PlayerHuman player1 = mock(PlayerHuman.class);
        PlayerHuman player2 = mock(PlayerHuman.class);

        when(state.getPlayer1()).thenReturn(player1);
        when(state.getPlayer2()).thenReturn(player2);
        when(state.nextPlayer()).thenReturn(player1);

        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.initialize(state);

        gamePresenter.makeMove(vertex);
        verify(player1).makeMove(vertex);
//        PlayerHuman[] players = gamePresenter.getPlayerHumans();
//        verify(players[0]).makeMove(vertex);
    }

    @Test
    void updateTestNoWinner() {
        GameView gameView = mock(GameView.class);
        when(gameView.taskRunner()).thenReturn(new SimpleTaskRunner());
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        State state = mock(State.class);
        Edge edge = new Edge(new Vertex(1,1),new Vertex(1,2));
        when(state.getWinner()).thenReturn(null);
        when(state.getBall()).thenReturn(mock(Vertex.class));

        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.initialize(state);
        gamePresenter.update(edge);
        verify(gameView).addEdge(any());

    }

    @Test
    void updateTestWinner() {
        GameView gameView = mock(GameView.class);
        when(gameView.taskRunner()).thenReturn(new SimpleTaskRunner());
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        State state = mock(State.class);
        Edge edge = new Edge(new Vertex(1,1),new Vertex(1,2));
        Player player = mock(Player.class);

        when(state.getWinner()).thenReturn(player);
        when(state.getBall()).thenReturn(mock(Vertex.class));

        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.initialize(state);

        gamePresenter.update(edge);
        verify(gameView).addEdge(any());
        verify(gameView).displayWinner(player.getName());
    }

    @Test
    void updateTestCurrentPlayer() {
        GameView gameView = mock(GameView.class);
        when(gameView.taskRunner()).thenReturn(new SimpleTaskRunner());
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        State state = mock(State.class);
        Edge edge = new Edge(new Vertex(1,1),new Vertex(1,2));
        Player player = mock(Player.class);

        when(state.getWinner()).thenReturn(null);
        when(state.getCurrentPlayer()).thenReturn(player);
        when(player.getName()).thenReturn("name");
        when(state.getBall()).thenReturn(mock(Vertex.class));

        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.initialize(state);
        gamePresenter.update(edge);
        verify(gameView).addEdge(any());
        verify(gameView).setCurrentPlayer("name");

    }

    @Test
    void getPlayerNamesTest(){
        GameView gameView = mock(GameView.class);
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        State state = mock(State.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        String name1 = "name1";
        String name2 = "name2";

        when(player1.getName()).thenReturn(name1);
        when(player2.getName()).thenReturn(name2);
        when(state.getPlayer1()).thenReturn(player1);
        when(state.getPlayer2()).thenReturn(player2);

        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);
        gamePresenter.initialize(state);

        List<String> names = gamePresenter.getPlayerNames();

        assertTrue(names.contains(name1));
        assertTrue(names.contains(name2));
        assertEquals(names.size(), 2);

    }

    @Test
    void okTest(){
        GameView gameView = mock(GameView.class);
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        GamePresenter gamePresenter = new GamePresenter(gameView, presenterFactory);

        gamePresenter.ok();

        verify(gameView).close();
        verify(presenterFactory).openMenuView();
    }

}
