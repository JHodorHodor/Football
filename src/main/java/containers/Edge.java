package containers;

public class Edge implements Comparable<Edge> {
    private Vertex v1,v2;
    public Edge(Vertex v1, Vertex v2){
        if(v1.compareTo(v2)>0) {
            this.v1 = v1;
            this.v2 = v2;
        }
        else{
            this.v1=v2;
            this.v2=v1;
        }
    }
    public Edge(Vertex v1, Move m){
        this(v1,new Vertex(v1,m));
    }

    public Edge(Edge e){
        this.v1 = new Vertex(e.v1);
        this.v2 = new Vertex(e.v2);
    }

    @Override
    public boolean equals(Object e) {
        if(e instanceof Edge)
           return this.v1.equals(((Edge) e).v1) && this.v2.equals(((Edge) e).v2);
        return super.equals(e);
    }

    @Override
    public int compareTo(Edge edge) {
        if(edge.v1.equals(this.v1)) return this.v2.compareTo(edge.v2);
        return this.v1.compareTo(edge.v1);
    }

    public boolean contain(Vertex v){
        return v1.equals(v) || v2.equals(v);
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    @Override
    public String toString(){ //for debugging purposes ONLY
        return v1+" "+v2;
    }
}
