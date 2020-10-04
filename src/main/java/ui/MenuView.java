package ui;

public interface MenuView {
    void initialize(MenuPresenter menuPresenter);
    void open();
    @SuppressWarnings("unused")
    void close();
}
