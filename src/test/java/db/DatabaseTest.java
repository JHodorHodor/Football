package db;

import containers.LoadedGameComponents;
import containers.Move;
import containers.PlayerType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DatabaseTest {

    @Test
    void testLoadGamePlayersHumanSimpleAI() {
        DatabaseEngine engine = mock(DatabaseEngine.class);
        doAnswer(invocation -> {
            DatabaseEngine.PlayersBuilder builder = invocation.getArgument(0);
            builder.buildPlayers(0);
            builder.buildPlayers(1);
            return null;
        }).when(engine).readPlayers(any(DatabaseEngine.PlayersBuilder.class));
        Database database = new Database(engine);
        LoadedGameComponents loadedGameComponents = database.loadGame();
        assertEquals(PlayerType.Human, loadedGameComponents.getPlayerList().get(0));
        assertEquals(PlayerType.MediumAI, loadedGameComponents.getPlayerList().get(1));
    }

    @Test
    void testLoadGameEmptyBoard() {
        DatabaseEngine engine = mock(DatabaseEngine.class);
        doAnswer(invocation -> {
            DatabaseEngine.PlayersBuilder builder = invocation.getArgument(0);
            builder.buildPlayers(0);
            builder.buildPlayers(1);
            return null;
        }).when(engine).readPlayers(any(DatabaseEngine.PlayersBuilder.class));
        Database database = new Database(engine);
        LoadedGameComponents loadedGameComponents = database.loadGame();
        assertThat(loadedGameComponents.getMoveList()).isEmpty();
        assertThat(loadedGameComponents.getPlayerList()).hasSize(2);
    }

    @Test
    void testAddMove() {
        Move move1 = new Move(0, 1);
        DatabaseEngine engine = mock(DatabaseEngine.class);
        doAnswer(invocation -> {
            DatabaseEngine.PlayersBuilder builder = invocation.getArgument(0);
            builder.buildPlayers(0);
            builder.buildPlayers(0);
            return null;
        }).when(engine).readPlayers(any(DatabaseEngine.PlayersBuilder.class));
        Database database = new Database(engine);
        database.loadGame();
        database.addMove(move1);
        verify(engine).insertMove(move1);
    }


    @Test
    void testLoadGameOneMove() {
        Move move1 = new Move(0, 1);
        DatabaseEngine engine = mock(DatabaseEngine.class);
        doAnswer(invocation -> {
            DatabaseEngine.MoveListBuilder builder = invocation.getArgument(0);
            builder.buildMove(move1.getLeftRight(), move1.getUpDown());
            return null;
        }).when(engine).readMoves(any(DatabaseEngine.MoveListBuilder.class));
        doAnswer(invocation -> {
            DatabaseEngine.PlayersBuilder builder = invocation.getArgument(0);
            builder.buildPlayers(0);
            builder.buildPlayers(0);
            return null;
        }).when(engine).readPlayers(any(DatabaseEngine.PlayersBuilder.class));
        Database database = new Database(engine);
        assertThat(database.loadGame().getMoveList()).containsExactly(move1);
    }

    @Test
    void testLoadGameMultipleMoves() {
        Move move1 = new Move(1, 0);
        Move move2 = new Move(0, 1);
        DatabaseEngine engine = mock(DatabaseEngine.class);
        doAnswer(invocation -> {
            DatabaseEngine.MoveListBuilder builder = invocation.getArgument(0);
            builder.buildMove(move1.getLeftRight(), move1.getUpDown());
            builder.buildMove(move2.getLeftRight(), move2.getUpDown());
            return null;
        }).when(engine).readMoves(any(DatabaseEngine.MoveListBuilder.class));

        doAnswer(invocation -> {
            DatabaseEngine.PlayersBuilder builder = invocation.getArgument(0);
            builder.buildPlayers(0);
            builder.buildPlayers(0);
            return null;
        }).when(engine).readPlayers(any(DatabaseEngine.PlayersBuilder.class));

        Database database = new Database(engine);
        LoadedGameComponents loadedGameComponents = database.loadGame();
        assertThat(loadedGameComponents.getMoveList()).containsExactly(move1, move2);
    }

    @Test
    void testCreateNewGame() {
        DatabaseEngine engine = mock(DatabaseEngine.class);
        PlayerType player1 = PlayerType.Human;
        PlayerType player2 = PlayerType.MediumAI;
        Database db = new Database(engine);
        db.createNewGame(player1, player2);
        verify(engine).createNewGame(player1.ordinal(), player2.ordinal());
    }

    @Test
    void testIsGameSaved() {
        DatabaseEngine engine = mock(DatabaseEngine.class);
        Database db = new Database(engine);

        when(engine.isGameSaved()).thenReturn(true);
        assertTrue(db.isGameSaved());

        when(engine.isGameSaved()).thenReturn(false);
        assertFalse(db.isGameSaved());
    }
}