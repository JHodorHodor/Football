package logic;

import containers.Edge;
import containers.Move;
import containers.Vertex;
import exceptions.IllegalMoveException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class Game implements State, StateChanger {

    public static final int LEFT_BARRIER = 0;
    public static final int RIGHT_BARRIER = 8;
    public static final int LOW_BARRIER = 0;
    public static final int HIGH_BARRIER = 10;
    public static final int LEFT_POLE = 3;
    public static final int RIGHT_POLE = 5;


    private Set<Edge> pitch;
    private Vertex ball;
    private Edge newEdge;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player winner;
    private Set<Observer> observers;
    private ArrayList<Move> startingMoves;

    public Game(Player player1, Player player2, ArrayList<Move> startingMoves) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.winner = null;
        this.pitch = new TreeSet<>();
        this.ball = new Vertex((RIGHT_BARRIER + LEFT_BARRIER) / 2, (HIGH_BARRIER + LOW_BARRIER) / 2);
        this.newEdge = null;
        this.startingMoves = startingMoves;
        this.observers = new HashSet<>();
        initPitch();
    }

    public Game(Player player1, Player player2) {
        this(player1, player2, new ArrayList<>());
    }


//STATE IMPLEMENTATION:

    @Override
    public Player nextPlayer() {
        return currentPlayer;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public boolean isLegal(Move move) {
        if (isEdgeColored(new Edge(ball, move))) {
            return false;
        }
        if (Math.abs(move.getLeftRight()) > 1 || Math.abs(move.getUpDown()) > 1 || (move.getUpDown() == 0 && move.getLeftRight() == 0)) {
            return false;
        }
        int x = ball.getX() + move.getLeftRight();
        if (x < LEFT_BARRIER || x > RIGHT_BARRIER){
            return false;
        }

        int y = ball.getY() + move.getUpDown();
        if (y < LOW_BARRIER - 1 || y > HIGH_BARRIER + 1) {
            return false;
        }
        return (y >= LOW_BARRIER && y <= HIGH_BARRIER) || (ball.getX() <= RIGHT_POLE && ball.getX() >= LEFT_POLE && x <= RIGHT_POLE && x >= LEFT_POLE);
    }

    @Override
    public Edge getNewEdge() {
        return newEdge;
    }

    @Override
    public Vertex getBall() {
        return ball;
    }

    @Override
    public boolean isEdgeColored(Edge e) {
        return pitch.contains(e);
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


//STATE-CHANGER IMPLEMENTATION:

    @Override
    public synchronized void makeMove(Move move) throws IllegalMoveException {
        if (!isLegal(move)) throw new IllegalMoveException();
        Edge newEdge = new Edge(ball, move);
        pitch.add(newEdge);
        this.newEdge = newEdge;
        ball = new Vertex(ball, move);
        whatNext();
    }

    @Override
    public void startGame() {
        for (Move m : startingMoves) this.makeMove(m);
        player1.start();
        player2.start();
    }


//OBSERVABLE IMPLEMENTATION

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() { //we want to notify everyone other than us first, so that we don't change anything first
        boolean notifyCurr=false;
        for (Observer o : observers) {
            if(o==currentPlayer){
                notifyCurr=true;
                continue;
            }
            o.update(newEdge);
        }
        if(notifyCurr && currentPlayer!=null) currentPlayer.update(newEdge); //if current player is detached, don't notify him.
    }

//INIT METHODS:

    private void initPitch() {
        insertEdge(true, LOW_BARRIER, LEFT_BARRIER, LEFT_POLE);
        insertEdge(false, RIGHT_POLE, LOW_BARRIER - 1, LOW_BARRIER);
        insertEdge(true, LOW_BARRIER - 1, LEFT_POLE, RIGHT_POLE);
        insertEdge(false, LEFT_POLE, LOW_BARRIER - 1, LOW_BARRIER);
        insertEdge(true, LOW_BARRIER, RIGHT_POLE, RIGHT_BARRIER);

        insertEdge(true, HIGH_BARRIER, LEFT_BARRIER, LEFT_POLE);
        insertEdge(false, RIGHT_POLE, HIGH_BARRIER, HIGH_BARRIER + 1);
        insertEdge(true, HIGH_BARRIER + 1, LEFT_POLE, RIGHT_POLE);
        insertEdge(false, LEFT_POLE, HIGH_BARRIER, HIGH_BARRIER + 1);
        insertEdge(true, HIGH_BARRIER, RIGHT_POLE, RIGHT_BARRIER);

        insertEdge(false, LEFT_BARRIER, LOW_BARRIER, HIGH_BARRIER);

        insertEdge(false, RIGHT_BARRIER, LOW_BARRIER, HIGH_BARRIER);
    }


//PLAYERS MANAGEMENT:

    private void swapPlayers() {
        currentPlayer = (currentPlayer == player1 ? player2 : player1);
    }

    private void playerWins(Player player) {
        this.winner = player;
        this.currentPlayer = null;
    }


//PITCH MANAGEMENT:

    private void whatNext() {
        boolean player2won = false, player1won = false;
        for (int i = Game.LEFT_POLE; i <= Game.RIGHT_POLE; i++) {
            if (ball.equals(new Vertex(i, LOW_BARRIER - 1))) {
                playerWins(player2);
                player2won = true;
            }
        }
        for (int i = Game.LEFT_POLE; i <= Game.RIGHT_POLE; i++) {
            if (ball.equals(new Vertex(i, HIGH_BARRIER + 1))) {
                playerWins(player1);
                player1won = true;
            }
        }
        if (!player1won && !player2won) {
            int count = 0;
            for (Edge e : pitch)
                if (e.contain(ball))
                    count++;

            boolean moreLegalMoves = false;

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) continue;
                    if (isLegal(new Move(i, j))) moreLegalMoves = true;
                }
            }
            if (!moreLegalMoves) {
                swapPlayers();
                playerWins(currentPlayer);
            }

            if (count <= 1) swapPlayers();
        }
        notifyObservers();
    }

    private void insertEdge(boolean horizontal, int pos, int from, int to) { //allows for quick construction of lines
        if (horizontal) {
            for (int i = from; i < to; i++) {
                pitch.add(new Edge(new Vertex(i, pos), new Vertex(i + 1, pos)));
            }
        } else {
            for (int i = from; i < to; i++) {
                pitch.add(new Edge(new Vertex(pos, i), new Vertex(pos, i + 1)));
            }
        }

    }

    void uncolourEdge(Edge e) { //for inheritance purposes, not a nice solution, but the best I could think of
        if (!isEdgeColored(e)) return;
        pitch.remove(e);
    }


}