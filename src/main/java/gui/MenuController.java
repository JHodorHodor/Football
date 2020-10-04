package gui;

import containers.PlayerType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ui.MenuPresenter;

public class MenuController {
    private MenuPresenter menuPresenter;
    private Stage stage;
    @FXML
    ScrollPane scrollPane;

    void setMenuPresenter(MenuPresenter menuPresenter) {
        this.menuPresenter = menuPresenter;
    }

    void setMenuStage(Stage stage) {
        this.stage = stage;
    }

    void setOpponents() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0));
        grid.setHgap(20);
        int column = 0;
        for (PlayerType opponent : PlayerType.values()) {
            Button button = new Button(opponent.name());
            button.setMaxHeight(80);
            button.setMinHeight(80);
            button.setMaxWidth(115);
            button.setMinWidth(115);
            grid.add(button, column++, 0);
            button.setOnMouseClicked(e -> {
                menuPresenter.newGame(PlayerType.Human, opponent);
                this.stage.close();
            });
        }
        scrollPane.setContent(grid);
    }

    public void continueGame() {
        menuPresenter.continueGame();
        this.stage.close();
    }
}
