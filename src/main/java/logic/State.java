package logic;

import containers.Edge;
import containers.Move;
import containers.Vertex;

public interface State extends Observable{
    Player nextPlayer();
    Player getWinner();
    boolean isLegal(Move move);
    Edge getNewEdge();
    Vertex getBall();
    boolean isEdgeColored(Edge e);
    Player getPlayer1();
    Player getPlayer2();
    Player getCurrentPlayer();
}
