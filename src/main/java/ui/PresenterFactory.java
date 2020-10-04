package ui;

import logic.GameLoader;

public class PresenterFactory {

    private final ViewFactory viewFactory;
    private final GameLoader gameLoader;

    public PresenterFactory(ViewFactory viewFactory, GameLoader gameLoader) {
        this.viewFactory = viewFactory;
        this.gameLoader = gameLoader;
    }

    public void openMenuView() {
        MenuView menuView = viewFactory.createMenuView();
        MenuPresenter menuPresenter = new MenuPresenter(menuView, this, gameLoader);
        menuView.initialize(menuPresenter);
        menuView.open();
    }

    GamePresenter createGameView() {
        GameView gameView = viewFactory.createGameView();
        GamePresenter gamePresenter = new GamePresenter(gameView, this);
        gameView.initialize(gamePresenter);
        return gamePresenter;
    }

}
