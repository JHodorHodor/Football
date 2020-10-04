package ui;

import containers.GameSetUp;
import containers.PlayerType;
import logic.GameLoader;
import logic.Observable;
import logic.State;
import logic.StateChanger;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MenuPresenterTest {

    @Test
    void newGameTest() {
        MenuView menuView = mock(MenuView.class);
        GamePresenter gamePresenter = mock(GamePresenter.class);
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        PlayerType player = mock(PlayerType.class);
        PlayerType opponent = mock(PlayerType.class);
        GameLoader gameLoader = mock(GameLoader.class);
        GameSetUp gameSetUp = mock(GameSetUp.class);
        Observable observable = mock(Observable.class);
        StateChanger stateChanger = mock(StateChanger.class);
        State state = mock(State.class);

        when(presenterFactory.createGameView()).thenReturn(gamePresenter);

        when(gameSetUp.getObservable()).thenReturn(observable);
        when(gameSetUp.getStateChanger()).thenReturn(stateChanger);
        when(gameSetUp.getState()).thenReturn(state);

        when(gameLoader.getNewGame(player, opponent)).thenReturn(gameSetUp);

        MenuPresenter menuPresenter = new MenuPresenter(menuView, presenterFactory, gameLoader);
        menuPresenter.newGame(player, opponent);

        verify(presenterFactory).createGameView();
    }

    @Test
    void continueGameTest() {
        MenuView menuView = mock(MenuView.class);
        GamePresenter gamePresenter = mock(GamePresenter.class);
        PresenterFactory presenterFactory = mock(PresenterFactory.class);
        GameLoader gameLoader = mock(GameLoader.class);
        GameSetUp gameSetUp = mock(GameSetUp.class);
        Observable observable = mock(Observable.class);
        StateChanger stateChanger = mock(StateChanger.class);
        State state = mock(State.class);

        when(presenterFactory.createGameView()).thenReturn(gamePresenter);

        when(gameSetUp.getObservable()).thenReturn(observable);
        when(gameSetUp.getStateChanger()).thenReturn(stateChanger);
        when(gameSetUp.getState()).thenReturn(state);

        when(gameLoader.getContinueGame()).thenReturn(gameSetUp);

        MenuPresenter menuPresenter = new MenuPresenter(menuView, presenterFactory, gameLoader);
        menuPresenter.continueGame();

        verify(presenterFactory).createGameView();
    }
}
