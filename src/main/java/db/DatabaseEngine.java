package db;

import containers.Move;

public interface DatabaseEngine {
    void createNewGame(int i, int ordinal);

    interface MoveListBuilder {
        void buildMove(int x, int y);
    }

    interface PlayersBuilder {
        void buildPlayers(int type);
    }

    void insertMove(Move move);

    void readMoves(MoveListBuilder builder);

    void readPlayers(PlayersBuilder builder);

    boolean isGameSaved();
}
