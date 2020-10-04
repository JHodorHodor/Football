package containers;

import java.util.ArrayList;

public class LoadedGameComponents {
    private ArrayList<PlayerType> playerList;
    private ArrayList<Move> moveList;

    public LoadedGameComponents(ArrayList<PlayerType> playerList, ArrayList<Move> moveList) {
        this.playerList = playerList;
        this.moveList = moveList;
    }

    public ArrayList<PlayerType> getPlayerList() {
        return playerList;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }
}
