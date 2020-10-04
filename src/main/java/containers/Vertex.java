package containers;

public class Vertex implements Comparable<Vertex> {
    private int x,y;
    public Vertex(int x, int y){
        this.x=x;
        this.y=y;
    }
    public Vertex(Vertex vertex, Move move){
        this.x=vertex.x+move.getLeftRight();
        this.y=vertex.y+move.getUpDown();
    }

    public Vertex(Vertex vertex){
        this.x=vertex.getX();
        this.y=vertex.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object v){
        if(v instanceof Vertex) {
            return this.x==((Vertex) v).x && this.y==((Vertex) v).y;
        }
        return super.equals(v);

    }

    @Override
    public int compareTo(Vertex vertex) {
        if(this.x==vertex.x) return this.y-vertex.y;
        return  this.x-vertex.x;
    }

    @Override
    public String toString(){ //for debugging purposes ONLY
        return x+" "+y;
    }
}
