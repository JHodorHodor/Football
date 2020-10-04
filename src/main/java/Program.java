import db.Database;
import fileData.fileFactory;
import gui.Gui;
import gui.WindowFactory;
import logic.GameLoader;
import ui.PresenterFactory;

class Program {
    public static void main(String[] args) {
        Database db = new Database(fileFactory.engine());
        GameLoader gameLoader = new GameLoader(db);
        Gui.run(() -> new PresenterFactory(new WindowFactory(), gameLoader).openMenuView());
    }
}
