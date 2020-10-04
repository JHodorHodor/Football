package logic;

import containers.Edge;
import containers.Move;
import exceptions.IllegalMoveException;
import exceptions.IllegalUndoException;

import java.util.ArrayList;
import java.util.Stack;


public class CalcGame extends Game{ //extends Game functionality to be able to manipulate your current turn, useful for AI implementation
    private Stack<Move> thisTurn;
    private boolean endOfTurn;


    CalcGame(Player player1, Player player2, ArrayList<Move> startingMoves) {
        super(player1,player2,startingMoves);
        thisTurn=new Stack<>();
    }

    boolean turnChanged(){
        return endOfTurn;
    }

    @Override
    public void makeMove(Move move) throws IllegalMoveException {

        Player p1 = nextPlayer();
        thisTurn.push(move);
        try{
            super.makeMove(move);
        }catch (IllegalMoveException e){
            thisTurn.pop();
            throw e;
        }
        if(nextPlayer()!=p1) endOfTurn=true;
    }

    boolean hasLegalMoves(){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(i==0 && j==0) continue;
                if(isLegal(new Move(i,j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void notifyObservers() {
        //do nothing
    }


    void endTurn(){
        endOfTurn=false;
        thisTurn.clear();
    }

    void undoMove() throws IllegalUndoException {
        if(thisTurn.empty()){
            throw new IllegalUndoException();
        }
        Move move = thisTurn.pop();
        Move revMove = new Move(-move.getLeftRight(),-move.getUpDown());
        Edge toUncolour = new Edge(getBall(),revMove);
        uncolourEdge(toUncolour);
        super.makeMove(revMove);
        uncolourEdge(toUncolour);
        if(endOfTurn) endOfTurn=false;
    }

    void clearTurn(){
        while (true){
            try{
                undoMove();
            }catch (IllegalUndoException e){
                break;
            }
        }
    }
}
