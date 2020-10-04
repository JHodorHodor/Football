package gui;

import containers.Edge;
import containers.Vertex;
import exceptions.IllegalMoveException;
import exceptions.WrongPlayerException;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Game;
import ui.GamePresenter;
import ui.GameView;
import ui.TaskRunner;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GameWindow implements GameView {
    private Stage stage;
    private ObservableList<Node> rootChildren;

    private TextField playerLabel1;
    private TextField playerLabel2;
    private String styleActive;
    private String styleNotActive;

    private final int column_nr = Game.RIGHT_BARRIER - Game.LEFT_BARRIER + 1;
    private final int row_nr = Game.HIGH_BARRIER - Game.LOW_BARRIER + 1;
    private final int cell_width = 60;
    private final int cell_height = 60;
    private int windowHeight;


    private final GridPane gridPane = new GridPane();
    private final Map<Vertex, PitchField> pitch = new TreeMap<>();
    private final Image notSelected = new Image("pics/dot.png");
    private final Image selected = new Image("pics/selected.png");
    private final Image ball = new Image("pics/ball.png");
    private GamePresenter gamePresenter;


    @Override
    public void initialize(GamePresenter gamePresenter) {

        this.gamePresenter = gamePresenter;

        styleActive =
                "    -fx-background-color: rgba(0, 0, 128, 0.7);\n" +
                        "    -fx-text-fill: #ffffff;\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-alignment: center ;";
        styleNotActive =
                "    -fx-background-color: rgba(0, 0, 128, 0.0);\n" +
                        "    -fx-text-fill: #ffffff;\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-alignment: center ;";

        for (int i = 0; i <= row_nr + 1; i++) {
            RowConstraints row = new RowConstraints(cell_height);
            gridPane.getRowConstraints().add(row);
        }
        for (int i = 0; i <= column_nr + 1; i++) {
            ColumnConstraints column = new ColumnConstraints(cell_width);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 1; i <= row_nr; i++)
            for (int j = 1; j <= column_nr; j++)
                addButton(new Vertex(j - 1 - Game.LEFT_BARRIER, i - 1 - Game.LOW_BARRIER));


        for (int i = Game.LEFT_POLE; i <= Game.RIGHT_POLE; i++) {

            addButton(new Vertex(i, -1));
            addButton(new Vertex(i, row_nr));
        }

        //init ball
        pitch.get(new Vertex((Game.RIGHT_BARRIER + Game.LEFT_BARRIER) / 2, (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2)).setImages(ball, ball, true);

        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(51, 131, 75), CornerRadii.EMPTY, Insets.EMPTY)));
        stage = new Stage();
        Group root = new Group();
        rootChildren = root.getChildren();

        windowHeight = cell_height * (2 + row_nr);
        int windowWidth = cell_width * (2 + column_nr);
        Scene scene = new Scene(root, windowWidth,
                windowHeight);
        rootChildren.add(gridPane);
        stage.setScene(scene);

        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> {
            windowEvent.consume();
            close();
        });
        stage.setResizable(false);
    }

    private void setFillOptions(Button button) {
        button.setMaxWidth(cell_width);
        button.setMaxHeight(cell_height);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
    }

    private void addButton(Vertex v) {
        ImageView imageView = new ImageView(notSelected);
        imageView.setFitHeight(cell_height);
        imageView.setFitWidth(cell_width);

        Button button = new Button();
        button.setPadding(Insets.EMPTY);
        button.setStyle(
                "-fx-faint-focus-color: transparent;" +
                        "-fx-focus-color: transparent;" +
                        "-fx-background-color: transparent;"
        );
        button.setOnMouseClicked(e -> {
            try {
                gamePresenter.makeMove(v);
            } catch (IllegalMoveException | WrongPlayerException exception) {
                //System.out.println("ignore");
            }
        });
        gridPane.add(button, v.getX() + 1, v.getY() + 1);
        setFillOptions(button);

        PitchField pf = new PitchField(button, imageView);
        pf.setImages(notSelected, selected, false);
        pitch.put(v, pf);
    }

    @Override
    public void addEdge(Edge edge) {
        Line line = new Line();

        PitchField pf1 = pitch.get(edge.getV1());
        PitchField pf2 = pitch.get(edge.getV2());

        line.setStartX(pf1.getButton().getLayoutX() + (this.cell_width >> 1));
        line.setStartY(pf1.getButton().getLayoutY() + (this.cell_height >> 1));
        line.setEndX(pf2.getButton().getLayoutX() + (this.cell_width >> 1));
        line.setEndY(pf2.getButton().getLayoutY() + (this.cell_height >> 1));

        if (pf1.isHasBall()) {
            pf1.setImages(notSelected, selected, false);
            pf2.setImages(ball, ball, true);
        } else if (pf2.isHasBall()) {
            pf2.setImages(notSelected, selected, false);
            pf1.setImages(ball, ball, true);
        }
        rootChildren.add(line);

    }

    @Override
    public void setCurrentPlayer(String name) {
        if(playerLabel1.getText().equals(name)) {
            playerLabel1.setStyle(styleActive);
            playerLabel2.setStyle(styleNotActive);
        }
        if(playerLabel2.getText().equals(name)) {
            playerLabel2.setStyle(styleActive);
            playerLabel1.setStyle(styleNotActive);
        }

    }

    @Override
    public void displayWinner(String winner) {
        TextField winnerField = new TextField();
        winnerField.setText(winner + " wins!");
        winnerField.setStyle(
                "    -fx-background-color: rgba(0, 128, 0, 0.7);\n" +
                        "    -fx-text-fill: #ffffff;\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-alignment: center ;");
        winnerField.setEditable(false);
        winnerField.setLayoutX(((row_nr >> 1) - 1) * cell_width);
        winnerField.setLayoutY(((column_nr >> 1) + 2) * cell_height);
        winnerField.setMinSize(3 * cell_width, cell_height);
        rootChildren.add(winnerField);

        Button ok = new Button();
        ok.setText("OK");
        ok.setStyle(
                "    -fx-background-color: rgba(0, 0, 128, 0.7);\n" +
                        "    -fx-text-fill: #ffffff;\n" +
                        "    -fx-background-radius: 30;\n" +
                        "    -fx-alignment: center ;");
        ok.setOnAction(e -> gamePresenter.ok());
        ok.setLayoutX((row_nr >> 1) * cell_width);
        ok.setLayoutY(((column_nr >> 1) + 3) * cell_height);
        ok.setMinSize(cell_width, cell_height);
        rootChildren.add(ok);
    }

    @Override
    public void open() {
        stage.show();
        initPitch();
        initPlayerLabels();
    }

    @Override
    public void close() {
        stage.close();
    }

    @Override
    public TaskRunner taskRunner() {
        return new InApplicationThreadTaskRunner();
    }


    private void initPitch() {
        insertEdge(true, Game.LOW_BARRIER, Game.LEFT_BARRIER, Game.LEFT_POLE);
        insertEdge(false, Game.RIGHT_POLE, Game.LOW_BARRIER - 1, Game.LOW_BARRIER);
        insertEdge(true, Game.LOW_BARRIER - 1, Game.LEFT_POLE, Game.RIGHT_POLE);
        insertEdge(false, Game.LEFT_POLE, Game.LOW_BARRIER - 1, Game.LOW_BARRIER);
        insertEdge(true, Game.LOW_BARRIER, Game.RIGHT_POLE, Game.RIGHT_BARRIER);

        insertEdge(true, Game.HIGH_BARRIER, Game.LEFT_BARRIER, Game.LEFT_POLE);
        insertEdge(false, Game.RIGHT_POLE, Game.HIGH_BARRIER, Game.HIGH_BARRIER + 1);
        insertEdge(true, Game.HIGH_BARRIER + 1, Game.LEFT_POLE, Game.RIGHT_POLE);
        insertEdge(false, Game.LEFT_POLE, Game.HIGH_BARRIER, Game.HIGH_BARRIER + 1);
        insertEdge(true, Game.HIGH_BARRIER, Game.RIGHT_POLE, Game.RIGHT_BARRIER);

        insertEdge(false, Game.LEFT_BARRIER, Game.LOW_BARRIER, Game.HIGH_BARRIER);
        insertEdge(false, Game.RIGHT_BARRIER, Game.LOW_BARRIER, Game.HIGH_BARRIER);

    }

    private void insertEdge(boolean horizontal, int pos, int from, int to) { //allows for quick construction of lines
        if (horizontal) {
            for (int i = from; i < to; i++) {
                addEdge(new Edge(new Vertex(i, pos), new Vertex(i + 1, pos)));
            }
        } else {
            for (int i = from; i < to; i++) {
                addEdge(new Edge(new Vertex(pos, i), new Vertex(pos, i + 1)));
            }
        }
    }

    private void initPlayerLabels(){

        List<String> names = gamePresenter.getPlayerNames();
        playerLabel1 = new TextField();
        setLabel(playerLabel1, names.get(0));
        playerLabel2 = new TextField();
        setLabel(playerLabel2, names.get(1));
        playerLabel2.setLayoutY(windowHeight - cell_height);
        setCurrentPlayer(gamePresenter.getCurrentPlayer());
    }

    private void setLabel(TextField label, String name){
        label.setText(name);
        label.setMinSize(3 * cell_width, cell_height);
        label.setStyle(styleNotActive);
        label.setEditable(false);
        rootChildren.add(label);
    }

}
