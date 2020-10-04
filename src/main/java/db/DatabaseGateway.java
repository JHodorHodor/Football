package db;

import containers.LoadedGameComponents;
import containers.Move;
import containers.PlayerType;

public interface DatabaseGateway {
    void addMove(Move move);

    boolean isGameSaved();

    LoadedGameComponents loadGame();

    void createNewGame(PlayerType playerType1, PlayerType playerType2);
}
