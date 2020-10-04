package logic;

import containers.GameSetUp;
import containers.LoadedGameComponents;
import containers.PlayerType;
import db.Database;
import exceptions.GameNotSavedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameLoaderTest {

    @Test
    void getNewGameTest() {
       Database db = mock(Database.class);
       Player playerOne = mock(Player.class);
       Player playerTwo = mock(Player.class);
       PlayerType typeOne = mock(PlayerType.class);
       when(typeOne.createInstance()).thenReturn(playerOne);
       PlayerType typeTwo = mock(PlayerType.class);
       when(typeTwo.createInstance()).thenReturn(playerTwo);
       GameLoader gameLoader = new GameLoader(db);
       GameSetUp g = gameLoader.getNewGame(typeOne,typeTwo);
       assertEquals(playerOne,g.getState().getPlayer1());
       assertEquals(playerTwo,g.getState().getPlayer2());
       verify(playerOne).initialize(eq(g.getState()),any());
       verify(playerTwo).initialize(eq(g.getState()),any());
    }

    @Test
    void getContinueGameSuccessTest() {
        Database db = mock(Database.class);
        when(db.isGameSaved()).thenReturn(true);
        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        PlayerType typeOne = mock(PlayerType.class);
        when(typeOne.createInstance()).thenReturn(playerOne);
        PlayerType typeTwo = mock(PlayerType.class);
        when(typeTwo.createInstance()).thenReturn(playerTwo);

        LoadedGameComponents loadedGameComponents = mock(LoadedGameComponents.class);
        ArrayList<PlayerType> arrayList = new ArrayList<>();
        arrayList.add(typeOne);
        arrayList.add(typeTwo);
        when(loadedGameComponents.getPlayerList()).thenReturn(arrayList);
        when(db.loadGame()).thenReturn(loadedGameComponents);
        GameLoader gameLoader = new GameLoader(db);
        GameSetUp g = gameLoader.getContinueGame();
        assertEquals(playerOne,g.getState().getPlayer1());
        assertEquals(playerTwo,g.getState().getPlayer2());
        verify(playerOne).initialize(eq(g.getState()),any());
        verify(playerTwo).initialize(eq(g.getState()),any());
    }

    @Test
    void getContinueGameFailTest() {
        Database database = mock(Database.class);
        when(database.isGameSaved()).thenReturn(false);
        GameLoader gameLoader = new GameLoader(database);
        assertThrows(GameNotSavedException.class, gameLoader::getContinueGame);
    }


}