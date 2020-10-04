package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MenuPresenter;
import ui.MenuView;

import java.io.IOException;

public class MenuWindow implements MenuView {
    private Stage stage;

    @Override
    public void initialize(MenuPresenter menuPresenter) {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();
            MenuController controller = loader.getController();
            controller.setMenuPresenter(menuPresenter);
            controller.setMenuStage(stage);
            controller.setOpponents();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        stage.show();
    }

    @Override
    public void close() {
        stage.close();
    }
}
