package db;

import containers.LoadedGameComponents;
import containers.Move;
import containers.PlayerType;

import java.util.ArrayList;

public class Database implements DatabaseGateway {
    private final DatabaseEngine databaseEngine;

    public Database(DatabaseEngine databaseEngine) {
        this.databaseEngine = databaseEngine;
    }

    @Override
    public LoadedGameComponents loadGame() {
        ArrayList<PlayerType> players = new ArrayList<>();
        databaseEngine.readPlayers((i) -> players.add(PlayerType.values()[i]));
        ArrayList<Move> moves = new ArrayList<>();
        databaseEngine.readMoves((x, y) -> moves.add(new Move(x, y)));
        return new LoadedGameComponents(players, moves);
    }

    @Override
    public void createNewGame(PlayerType player1, PlayerType player2) {
        databaseEngine.createNewGame(player1.ordinal(), player2.ordinal());
    }

    @Override
    public boolean isGameSaved() {
        return databaseEngine.isGameSaved();
    }

    @Override
    public void addMove(Move move) {
        databaseEngine.insertMove(move);
    }

}
