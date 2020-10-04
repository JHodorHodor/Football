package gui;

import ui.GameView;
import ui.MenuView;
import ui.ViewFactory;

public class WindowFactory implements ViewFactory {
    @Override
    public GameView createGameView() {
        return new GameWindow();
    }

    @Override
    public MenuView createMenuView() {
        return new MenuWindow();
    }
}
