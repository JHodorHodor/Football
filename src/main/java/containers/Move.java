package containers;

public class Move {
    private int upDown, leftRight;
    public Move(int leftRight, int upDown){
        this.leftRight=leftRight;
        this.upDown=upDown;
    }

    public Move(Vertex from, Vertex to){
        this.leftRight=to.getX()-from.getX();
        this.upDown=to.getY()-from.getY();
    }

    public int getLeftRight() {
        return leftRight;
    }
    public int getUpDown(){
        return upDown;
    }

    @Override
    public String toString() {
        return leftRight+" "+upDown;
    }

    @Override
    public boolean equals(Object move){
        if(move instanceof Move) {
            return ((Move) move).getLeftRight() == this.leftRight && ((Move) move).getUpDown() == this.upDown;
        }
        return super.equals(move);
    }

}
