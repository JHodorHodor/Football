package logic;

import containers.Edge;
import containers.Move;
import containers.Vertex;
import exceptions.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {


    @Test
    void attachNewTest(){
        Player p1 = mock(Player.class), p2 = mock(Player.class);
        Game g = new Game(p1, p2);
        Player p3 = mock(Player.class);

        g.attach(p3);
        g.notifyObservers();
        verify(p3).update(any());
        verifyNoMoreInteractions(p3);
    }

    @Test
    void detachTest(){
        Player p1 = mock(Player.class), p2 = mock(Player.class);
        Game g = new Game(p1, p2);
        g.attach(p1);
        g.detach(p1);
        g.notifyObservers();
        verifyNoMoreInteractions(p1);
    }

    @Test
    void startGameTest(){
        Player p1 = mock(Player.class), p2 = mock(Player.class);
        Move m = new Move(-1,1);
        ArrayList<Move> arrayList = new ArrayList<>(Collections.singletonList(m));
        Game g = new Game(p1, p2,arrayList);
        g.attach(p1);
        g.attach(p2);
        g.startGame();

        verify(p1).update(any());
        verify(p2).update(any());
        verify(p1).start();
        verify(p2).start();
        verifyNoMoreInteractions(p1,p2);
    }

    @Test
    void isLegalBadMoveTest(){
        Player p1 = mock(Player.class), p2 = mock(Player.class);
        Game g = new Game(p1, p2);
        assertFalse(g.isLegal(new Move(5,0)));
    }

    @Test
    void getPlayersTest(){
        Player p1 = mock(Player.class), p2 = mock(Player.class);
        Game g = new Game(p1, p2);
        assertEquals(p1,g.getPlayer1());
        assertEquals(p2,g.getPlayer2());
    }

    @Test
    void moveNotifyTest() {
        Player p1 = mock(Player.class), p2 = mock(Player.class);
        Game g = new Game(p1, p2);
        g.attach(p1);
        g.attach(p2);
        Move m = new Move(-1,1);
        g.makeMove(m);
        verify(p1).update(any());
        verify(p2).update(any());
    }

    @Test
    void basicGetBallPosTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2,g.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2,g.getBall().getY());
    }

    @Test
    void getBallPosMoveTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move m = new Move(1,1);
        g.makeMove(m);
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2+1,g.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2+1,g.getBall().getY());
    }

    @Test
    void initPitchIsEdgeColouredTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        // Poles
        assertTrue(g.isEdgeColored(new Edge(new Vertex(Game.RIGHT_POLE,Game.LOW_BARRIER),new Vertex(Game.RIGHT_POLE,Game.LOW_BARRIER-1))));
        assertTrue(g.isEdgeColored(new Edge(new Vertex(Game.LEFT_POLE,Game.LOW_BARRIER),new Vertex(Game.LEFT_POLE,Game.LOW_BARRIER-1))));
        assertTrue(g.isEdgeColored(new Edge(new Vertex(Game.LEFT_POLE,Game.HIGH_BARRIER),new Vertex(Game.LEFT_POLE,Game.HIGH_BARRIER+1))));
        assertTrue(g.isEdgeColored(new Edge(new Vertex(Game.RIGHT_POLE,Game.HIGH_BARRIER),new Vertex(Game.RIGHT_POLE,Game.HIGH_BARRIER+1))));

        // left and right
        for(int i=Game.LOW_BARRIER;i<Game.HIGH_BARRIER;i++){
            assertTrue(g.isEdgeColored(new Edge(new Vertex(Game.LEFT_BARRIER,i),new Vertex(Game.LEFT_BARRIER,i+1))));
            assertTrue(g.isEdgeColored(new Edge(new Vertex(Game.RIGHT_BARRIER,i),new Vertex(Game.RIGHT_BARRIER,i+1))));
        }

        //low and high
        for(int i=Game.LEFT_BARRIER;i<Game.RIGHT_BARRIER;i++){
            int inGoal=0;
            if(i>=Game.LEFT_POLE && i<Game.RIGHT_POLE) inGoal=1;
            assertTrue(g.isEdgeColored(new Edge(new Vertex(i,Game.LOW_BARRIER-inGoal),new Vertex(i+1,Game.LOW_BARRIER-inGoal))));
            assertTrue(g.isEdgeColored(new Edge(new Vertex(i,Game.HIGH_BARRIER+inGoal),new Vertex(i+1,Game.HIGH_BARRIER+inGoal))));
        }

    }



    @Test
    void isEdgeColouredTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move m = new Move(1,1);
        g.makeMove(m);
        assertTrue(g.isEdgeColored(new Edge(new Vertex((Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2,(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2),
                new Vertex((Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2 + 1,(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2 + 1))));
    }

    @Test
    void checkLegalTaken(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move m = new Move(1,1);
        g.makeMove(m);
        Move rev = new Move(-1,-1);
        assertThrows(IllegalMoveException.class,() -> g.makeMove(rev));
    }

    @Test
    void checkLegalLeft(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move m = new Move(-1,0);
        for(int i=0;i<(Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2;i++){
            assertEquals((Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2-i,g.getBall().getX());
            assertDoesNotThrow(()->g.makeMove(m));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(m));
        assertEquals(Game.LEFT_BARRIER,g.getBall().getX());
    }

    @Test
    void checkLegalRight(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move m = new Move(1,0);
        for(int i=0;i<(Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2;i++) {
            assertEquals((Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2+i,g.getBall().getX());
            assertDoesNotThrow(() -> g.makeMove(m));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(m));
        assertEquals(Game.RIGHT_BARRIER,g.getBall().getX());
    }

    @Test
    void bottomLeftOutOfGoalOutOfBounds(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move left = new Move(-1,0);
        for(int i=(Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2; i>=Game.LEFT_POLE; i--){
            assertEquals(i,g.getBall().getX());
            assertDoesNotThrow(() -> g.makeMove(left));
        }

        Move down = new Move(0,-1);
        for(int i=(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2; i>Game.LOW_BARRIER;i--){
            assertEquals(i,g.getBall().getY());
            assertDoesNotThrow(() -> g.makeMove(down));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(down));
    }

    @Test
    void bottomRightOutOfGoalOutOfBounds(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move right = new Move(1,0);
        for(int i=(Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2; i<=Game.RIGHT_POLE; i++){
            assertEquals(i,g.getBall().getX());
            assertDoesNotThrow(() -> g.makeMove(right));
        }

        Move down = new Move(0,-1);
        for(int i=(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2; i>Game.LOW_BARRIER;i--){
            assertEquals(i,g.getBall().getY());
            assertDoesNotThrow(() -> g.makeMove(down));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(down));
    }


    @Test
    void topRightOutOfGoalOutOfBounds(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move right = new Move(1,0);
        for(int i=(Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2; i<=Game.RIGHT_POLE; i++){
            assertEquals(i,g.getBall().getX());
            assertDoesNotThrow(() -> g.makeMove(right));
        }

        Move up = new Move(0,1);
        for(int i=(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2; i<Game.HIGH_BARRIER;i++){
            assertEquals(i,g.getBall().getY());
            assertDoesNotThrow(() -> g.makeMove(up));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(up));
    }

    @Test
    void topLeftOutOfGoalOutOfBounds(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move left = new Move(-1,0);
        for(int i=(Game.RIGHT_BARRIER-Game.LEFT_BARRIER)/2; i>=Game.LEFT_POLE; i--){
            assertEquals(i,g.getBall().getX());
            assertDoesNotThrow(() -> g.makeMove(left));
        }
        Move up = new Move(0,1);
        for(int i=(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2; i<Game.HIGH_BARRIER;i++){
            assertEquals(i,g.getBall().getY());
            assertDoesNotThrow(() -> g.makeMove(up));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(up));
    }

    @Test
    void topInGoalOutOfBounds(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move up = new Move(0,1);
        for(int i=(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2; i<Game.HIGH_BARRIER+1;i++){
            assertEquals(i,g.getBall().getY());
            assertDoesNotThrow(() -> g.makeMove(up));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(up));
    }

    @Test
    void bottomInGoalOutOfBounds(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move down= new Move(0,-1);
        for(int i=(Game.HIGH_BARRIER-Game.LOW_BARRIER)/2; i>Game.LOW_BARRIER-1;i--){
            assertEquals(i,g.getBall().getY());
            assertDoesNotThrow(() -> g.makeMove(down));
        }
        assertThrows(IllegalMoveException.class,() -> g.makeMove(down));
    }

    @Test
    void makeMoveReturnValueFalseTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        assertEquals(g.nextPlayer(),p1);
        Move down= new Move(0,-1);
        g.makeMove(down);
        assertEquals(g.nextPlayer(),p2);
    }

    @Test
    void makeMoveReturnValueTrueTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        assertEquals(g.nextPlayer(),p1);
        Move down= new Move(0,-1);
        g.makeMove(down);
        assertEquals(g.nextPlayer(),p2);
        Move leftUp = new Move(-1,1);
        g.makeMove(leftUp);
        assertEquals(g.nextPlayer(),p1);
        Move right = new Move(1,0);
        g.makeMove(right);
        assertEquals(g.nextPlayer(),p1);
    }

    @Test
    void goal1Test(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move down = new Move(0, -1);
        for(int i = 0; i < (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 ; i++){
            g.makeMove(down);
            assertNull(g.getWinner());
        }
        g.makeMove(down);
        assertEquals(p2,g.getWinner());
    }
    @Test
    void goal2Test(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move up = new Move(0, 1);
        for(int i = 0; i < (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 ; i++){
            g.makeMove(up);
            assertNull(g.getWinner());
        }
        g.makeMove(up);
        assertEquals(p1,g.getWinner());
    }

    @Test
    void blockInCornerTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game g = new Game(p1, p2);
        Move up = new Move(0, 1);
        Move left = new Move(-1, 0);
        Move leftUp = new Move(-1, 1);
        int x=0;
        for(; x < (Game.HIGH_BARRIER + Game.LOW_BARRIER) / 2 - 1; x++){
            g.makeMove(up);

        }
        int i = 0;
        for(; i < (Game.LEFT_BARRIER + Game.RIGHT_BARRIER) / 2 - 1; i++){
            g.makeMove(left);
        }
        Player blocked = g.nextPlayer();
        g.makeMove(leftUp);
        assertEquals((blocked==p1? p2 : p1),g.getWinner());
    }

    @Test
    void blockInEdgeTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game game = new Game(p1, p2);
        Move left = new Move(-1, 0);
        Move down = new Move(0, -1);
        Move leftUp = new Move(-1, 1);
        Move rightUp = new Move(1, 1);
        int i = 0;
        for(; i < (Game.LEFT_BARRIER + Game.RIGHT_BARRIER) / 2; i++) {
            game.makeMove(left);
        }
        game.makeMove(rightUp);
        game.makeMove(down);
        game.makeMove(down);
        Player blocked = game.nextPlayer();
        game.makeMove(leftUp);
        assertEquals((blocked==p1? p2 : p1),game.getWinner());

    }

    @Test
    void newEdgeTest(){
        Player p1=mock(Player.class),p2=mock(Player.class);
        Game game = new Game(p1, p2);
        game.makeMove(new Move(-1,-1));
        Vertex v2 = game.getBall();
        game.makeMove(new Move(-1,-1));
        Vertex v3 = game.getBall();

        Edge newEdge = game.getNewEdge();

        assertTrue(newEdge.contain(v2));
        assertTrue(newEdge.contain(v3));
    }

}