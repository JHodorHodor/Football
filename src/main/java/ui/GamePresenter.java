package ui;

import containers.Edge;
import containers.Vertex;
import exceptions.IllegalMoveException;
import exceptions.WrongPlayerException;
import logic.Observer;
import logic.Player;
import logic.PlayerHuman;
import logic.State;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter implements Observer {

    private final PresenterFactory presenterFactory;
    private final GameView gameView;
    private State state;
    private PlayerHuman player1 = null;
    private PlayerHuman player2 = null;

    private TaskRunner gameViewTaskRunner;


    GamePresenter(GameView gameView, PresenterFactory presenterFactory) {
        this.gameView = gameView;
        this.presenterFactory = presenterFactory;
        gameViewTaskRunner= gameView.taskRunner();
    }

    public void initialize(State state) {
        this.state = state;
        Player player1 = state.getPlayer1();
        Player player2 = state.getPlayer2();

        if (player1 instanceof PlayerHuman)
            this.player1 = (PlayerHuman) player1;

        if (player2 instanceof PlayerHuman)
            this.player2 = (PlayerHuman) player2;

    }

    public void makeMove(Vertex vertex) throws IllegalMoveException, WrongPlayerException {
        Player currentPlayer = state.nextPlayer();
        if (currentPlayer == player1 || currentPlayer == player2) {
            ((PlayerHuman) currentPlayer).makeMove(vertex);
        }
    }


    @Override
    public void update(Edge edge) {
        Edge edge1=new Edge(edge);
        gameViewTaskRunner.runTask(()->gameView.addEdge(edge1));
        Player winner = state.getWinner();
        Player currentPlayer = state.getCurrentPlayer();
        if (winner != null)
            gameViewTaskRunner.runTask(()->gameView.displayWinner(winner.getName()));
        if(currentPlayer != null)
            gameViewTaskRunner.runTask(()->gameView.setCurrentPlayer(currentPlayer.getName()));
    }

    void open() {
        gameView.open();
    }

    public void ok() {
        gameView.close();
        presenterFactory.openMenuView();
    }

    public List<String> getPlayerNames(){
        List<String> names = new ArrayList<>();
        names.add(state.getPlayer1().getName());
        names.add(state.getPlayer2().getName());
        return names;
    }

    public String getCurrentPlayer(){
        return state.getCurrentPlayer().getName();
    }
}
