package logic;

import containers.Edge;
import containers.Move;
import exceptions.IllegalMoveException;
import exceptions.IllegalUndoException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalcGameTest {
    @Test
    void moveNotifyTest(){
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        CalcGame calcGame = new CalcGame(p1,p2,new ArrayList<>());
        calcGame.makeMove(new Move(-1,1));
        verifyNoMoreInteractions(p1,p2);
    }

    @Test
    void moveAndUndoTest(){
        CalcGame calcGame = new CalcGame(null,null,new ArrayList<>());
        Move move = new Move(1,1);
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2,calcGame.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2,calcGame.getBall().getY());
        Edge e=new Edge(calcGame.getBall(),move);
        calcGame.makeMove(move);
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2+1,calcGame.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2+1,calcGame.getBall().getY());
        assertTrue(calcGame.isEdgeColored(e));
        assertDoesNotThrow(calcGame::undoMove);
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2,calcGame.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2,calcGame.getBall().getY());
        assertFalse(calcGame.isEdgeColored(e));
    }

    @Test
    void clearTest(){
        CalcGame calcGame = new CalcGame(null,null,new ArrayList<>());
        Move move = new Move(1,1);
        calcGame.makeMove(move);
        calcGame.makeMove(move);
        calcGame.clearTurn();
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2,calcGame.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2,calcGame.getBall().getY());
    }

    @Test
    void illegalMoveAndUndoTest(){
        CalcGame calcGame = new CalcGame(null,null,new ArrayList<>());
        Move m = new Move(1,1);
        calcGame.makeMove(m);
        Move rev = new Move(-1,-1);
        assertThrows(IllegalMoveException.class,() -> calcGame.makeMove(rev));
        assertDoesNotThrow(calcGame::undoMove);
        assertEquals((Game.RIGHT_BARRIER+Game.LEFT_BARRIER)/2,calcGame.getBall().getX());
        assertEquals((Game.HIGH_BARRIER+Game.LOW_BARRIER)/2,calcGame.getBall().getY());

    }

    @Test
    void endTurnTest(){
        CalcGame calcGame = new CalcGame(null,null,new ArrayList<>());
        Move move = new Move(1,1);
        calcGame.makeMove(move);
        calcGame.endTurn();
        assertThrows(IllegalUndoException.class, calcGame::undoMove);
    }

    @Test
    void turnChangedTest(){
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        CalcGame calcGame = new CalcGame(p1,p2,new ArrayList<>());
        assertFalse(calcGame.turnChanged());
        Move m = new Move(-1,0);
        calcGame.makeMove(m);
        assertTrue(calcGame.turnChanged());
        assertDoesNotThrow(calcGame::undoMove);
        assertFalse(calcGame.turnChanged());
        calcGame.makeMove(m);
        assertTrue(calcGame.turnChanged());
        calcGame.endTurn();
        assertFalse(calcGame.turnChanged());

    }

    @Test
    void hasLegalMovesTrueTest(){
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);

        CalcGame cg = new CalcGame(p1, p2, new ArrayList<>());
        assertTrue(cg.hasLegalMoves());
    }

    @Test
    void hasLegalMovesFalseTest(){
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);

        CalcGame cg = new CalcGame(p1, p2, new ArrayList<>());


        Move left = new Move(-1, 0);
        Move down = new Move(0, -1);
        Move leftUp = new Move(-1, 1);
        Move rightUp = new Move(1, 1);
        int i = 0;
        for(; i < (Game.LEFT_BARRIER + Game.RIGHT_BARRIER) / 2; i++) {
            cg.makeMove(left);
        }
        cg.makeMove(rightUp);
        cg.makeMove(down);
        cg.makeMove(down);
        cg.makeMove(leftUp);

        assertFalse(cg.hasLegalMoves());
    }


}