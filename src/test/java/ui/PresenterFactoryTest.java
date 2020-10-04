package ui;

import logic.GameLoader;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PresenterFactoryTest {
    @Test
    void openMenuViewTest(){
        ViewFactory viewFactory = mock(ViewFactory.class);
        MenuView menuView = mock(MenuView.class);
        when(viewFactory.createMenuView()).thenReturn(menuView);
        GameLoader gameLoader = mock(GameLoader.class);
        PresenterFactory presenterFactory = new PresenterFactory(viewFactory,gameLoader);
        presenterFactory.openMenuView();
        verify(viewFactory).createMenuView();
        verify(menuView).initialize(any());
        verify(menuView).open();
        verifyNoMoreInteractions(menuView,gameLoader,viewFactory);

    }

    @Test
    void createGameViewTest(){
        ViewFactory viewFactory = mock(ViewFactory.class);
        GameView gameView = mock(GameView.class);
        when(viewFactory.createGameView()).thenReturn(gameView);
        GameLoader gameLoader = mock(GameLoader.class);
        PresenterFactory presenterFactory = new PresenterFactory(viewFactory,gameLoader);
        GamePresenter gamePresenter = presenterFactory.createGameView();
        verify(viewFactory).createGameView();
        verify(gameView).initialize(gamePresenter);
    }

}