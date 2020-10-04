package containters;

import containers.Move;
import containers.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VertexTest {

    @Test
    void constructByMoveTest(){
        Vertex v = new Vertex(5,6);
        Move m = mock(Move.class);

        when(m.getLeftRight()).thenReturn(2);
        when(m.getUpDown()).thenReturn(-5);

        Vertex afterMove = new Vertex(v,m);

        assertEquals(7, afterMove.getX());
        assertEquals(1, afterMove.getY());
    }

    @Test
    void equalsTrueTest(){
        Vertex v1 = new Vertex(5,6);
        Vertex v2 = new Vertex(5,6);

        assertEquals(v1,v2);
        assertEquals(v2,v1);
    }

    @Test
    void equalsFalseTest(){
        Vertex v1 = new Vertex(5,6);
        Vertex v2 = new Vertex(6,5);
        Vertex v3 = new Vertex(5,7);
        Vertex v4 = new Vertex(4,6);

        assertNotEquals(v1,v2);
        assertNotEquals(v2,v1);
        assertNotEquals(v1,v3);
        assertNotEquals(v3,v1);
        assertNotEquals(v1,v4);
        assertNotEquals(v4,v1);
    }

    @Test
    void equalsFalseTest2() {
        Vertex v1 = new Vertex(5, 6);
        Object o = new Object();

        assertNotEquals(v1, o);
    }

    @Test
    void compareTest(){
        Vertex v1 = new Vertex(1,2);
        Vertex v2 = new Vertex(2,3);
        Vertex v3 = new Vertex(2,0);
        Vertex v4 = new Vertex(1,5);

        assertTrue(v2.compareTo(v1) > 0);
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v3.compareTo(v1) > 0);
        assertTrue(v1.compareTo(v3) < 0);
        assertTrue(v4.compareTo(v1) > 0);
        assertTrue(v1.compareTo(v4) < 0);
    }
}
