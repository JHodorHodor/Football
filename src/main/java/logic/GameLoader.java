package logic;

import containers.*;
import db.Database;
import exceptions.GameNotSavedException;

import java.util.ArrayList;
import java.util.List;

public class GameLoader {
    private Database database;

    public GameLoader(Database database) {
        this.database = database;
    }

    public GameSetUp getContinueGame() {
        if (!database.isGameSaved()) throw new GameNotSavedException();
        LoadedGameComponents loadedGameComponents = database.loadGame();
        return constructGame(loadedGameComponents.getPlayerList().get(0), loadedGameComponents.getPlayerList().get(1), loadedGameComponents.getMoveList());
    }

    public GameSetUp getNewGame(PlayerType playerType1, PlayerType playerType2) {
        database.createNewGame(playerType1, playerType2);
        return constructGame(playerType1, playerType2, new ArrayList<>());
    }

    private GameSetUp constructGame(PlayerType playerType1, PlayerType playerType2, ArrayList<Move> moves) {

        List<Vertex> goals1 = new ArrayList<>();
        List<Vertex> goals2 = new ArrayList<>();
        for(int i = Game.LEFT_POLE; i <= Game.RIGHT_POLE; i++){
            goals1.add(new Vertex(i,Game.HIGH_BARRIER+1));
            goals2.add(new Vertex(i,Game.LOW_BARRIER-1));
        }

        Player player1 = playerType1.createInstance();
        player1.setName("Player 1");
        Player player2 = playerType2.createInstance();
        player2.setName("Player 2");
        if(player1 instanceof PlayerAI){
            ((PlayerAI) player1).calibrate(goals1,goals2);
        }
        if(player2 instanceof PlayerAI){
            ((PlayerAI) player2).calibrate(goals2,goals1);
        }
        Game game = new Game(player1, player2, moves);

        SavingStateChanger savingStateChanger = new SavingStateChangerImpl(game, game, this.database);
        ValidatingStateChanger validatingStateChanger1 = new ValidatingStateChangerImpl(game, savingStateChanger, player1);
        ValidatingStateChanger validatingStateChanger2 = new ValidatingStateChangerImpl(game, savingStateChanger, player2);

        player1.initialize(game, validatingStateChanger1);
        player2.initialize(game, validatingStateChanger2);

        return new GameSetUp(game, savingStateChanger, game);
    }
}
