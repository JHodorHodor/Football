package ui;

import containers.GameSetUp;
import containers.PlayerType;
import logic.GameLoader;

public class MenuPresenter {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final MenuView menuView;
    private final PresenterFactory presenterFactory;
    private final GameLoader gameLoader; // not sure if this is the right place

    MenuPresenter(MenuView menuView, PresenterFactory presenterFactory, GameLoader gameLoader) {
        this.menuView = menuView;
        this.presenterFactory = presenterFactory;
        this.gameLoader = gameLoader;
    }

    public void newGame(PlayerType playerType1, PlayerType playerType2) {
        GamePresenter gamePresenter = presenterFactory.createGameView();
        GameSetUp gameSetUp = gameLoader.getNewGame(playerType1, playerType2);
        gameSetUp.getObservable().attach(gamePresenter);
        gamePresenter.initialize(gameSetUp.getState());
        gamePresenter.open();
        gameSetUp.getStateChanger().startGame();
    }


    public void continueGame() {
        GamePresenter gamePresenter = presenterFactory.createGameView();
        GameSetUp gameSetUp = gameLoader.getContinueGame();
        gameSetUp.getObservable().attach(gamePresenter);
        gamePresenter.initialize(gameSetUp.getState());
        gamePresenter.open();
        gameSetUp.getStateChanger().startGame();
    }

}
