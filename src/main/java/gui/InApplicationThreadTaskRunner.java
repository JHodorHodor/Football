package gui;

import javafx.application.Platform;
import ui.TaskRunner;

public class InApplicationThreadTaskRunner implements TaskRunner {
    @Override
    public void runTask(Runnable r) {
        Platform.runLater(r);
    }
}
