package gui;

import javafx.application.Platform;

public class Gui {
    // Runnable will then be called on the JavaFX Application Thread.
    public static void run(Runnable runnable) { Platform.startup(runnable); }
}
