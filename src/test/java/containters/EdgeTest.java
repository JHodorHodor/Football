package containters;

import containers.Edge;
import containers.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EdgeTest {

    @Test
    void vertexInEdgeOrderTest(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);

        when(v1.compareTo(v2)).thenReturn(1);
        when(v2.compareTo(v1)).thenReturn(-1);

        Edge e1 = new Edge(v1,v2);
        Edge e2 = new Edge(v2,v1);

        assertEquals(e1.getV1(), v1);
        assertEquals(e1.getV2(), v2);
        assertEquals(e2.getV1(), v1);
        assertEquals(e2.getV2(), v2);
    }

    @Test
    void equalsTest1(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);

        when(v1.compareTo(v2)).thenReturn(1);
        when(v2.compareTo(v1)).thenReturn(-1);

        Edge e1 = new Edge(v1,v2);
        Edge e2 = new Edge(v2,v1);
        Edge e3 = new Edge(v1,v2);

        assertEquals(e1, e2);
        assertEquals(e1, e3);
    }

    @Test
    void equalsTest2(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);

        Edge e1 = new Edge(v1,v2);
        Object o = new Object();

        assertNotEquals(e1, o);
    }

    @Test
    void compareTest1(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);
        Vertex v3 = mock(Vertex.class);
        Vertex v4 = mock(Vertex.class);

        // v1 > v2
        when(v1.compareTo(v2)).thenReturn(1);
        when(v2.compareTo(v1)).thenReturn(-1);
        // v3 > v4
        when(v3.compareTo(v4)).thenReturn(1);
        when(v4.compareTo(v3)).thenReturn(-1);

        Edge e1 = new Edge(v1,v2);
        Edge e2 = new Edge(v3,v4);

        // v1 > v3
        when(v1.compareTo(v3)).thenReturn(1);
        when(v3.compareTo(v1)).thenReturn(-1);

        assertTrue(e1.compareTo(e2) > 0);
        assertTrue(e2.compareTo(e1) < 0);
    }

    @Test
    void compareTest2(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);
        Vertex v3 = mock(Vertex.class);

        // v1 > v2
        when(v1.compareTo(v2)).thenReturn(1);
        when(v2.compareTo(v1)).thenReturn(-1);
        // v1 > v3
        when(v1.compareTo(v3)).thenReturn(1);
        when(v3.compareTo(v1)).thenReturn(-1);

        Edge e1 = new Edge(v1,v2);
        Edge e2 = new Edge(v1,v3);

        // v2 > v3
        when(v2.compareTo(v3)).thenReturn(1);
        when(v3.compareTo(v2)).thenReturn(-1);

        assertTrue(e1.compareTo(e2) > 0);
        assertTrue(e2.compareTo(e1) < 0);
    }

    @Test
    void containsTest(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);
        Vertex v3 = mock(Vertex.class);

        Edge e = new Edge(v1,v2);

        assertTrue(e.contain(v1));
        assertTrue(e.contain(v2));
        assertFalse(e.contain(v3));
    }
}
