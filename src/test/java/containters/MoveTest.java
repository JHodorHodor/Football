package containters;

import containers.Move;
import containers.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoveTest {

    @Test
    void buildTest1(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);

        when(v1.getX()).thenReturn(17);
        when(v1.getY()).thenReturn(28);
        when(v2.getX()).thenReturn(-14);
        when(v2.getY()).thenReturn(39);

        Move m = new Move(v1,v2);

        assertEquals(-31, m.getLeftRight());
        assertEquals(11, m.getUpDown());
    }

    @Test
    void buildTest2(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);

        when(v1.getX()).thenReturn(8);
        when(v1.getY()).thenReturn(5);
        when(v2.getX()).thenReturn(8);
        when(v2.getY()).thenReturn(4);

        Move m = new Move(v1,v2);

        assertEquals(0, m.getLeftRight());
        assertEquals(-1, m.getUpDown());
    }

    @Test
    void equalsTrueTest(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);
        Vertex v3 = mock(Vertex.class);
        Vertex v4 = mock(Vertex.class);

        when(v1.getX()).thenReturn(0);
        when(v1.getY()).thenReturn(0);
        when(v2.getX()).thenReturn(3);
        when(v2.getY()).thenReturn(6);

        when(v3.getX()).thenReturn(1);
        when(v3.getY()).thenReturn(1);
        when(v4.getX()).thenReturn(4);
        when(v4.getY()).thenReturn(7);

        Move m1 = new Move(v1,v2);
        Move m2 = new Move(v3,v4);

        assertEquals(m1,m2);
        assertEquals(m2,m1);
    }

    @Test
    void equalsFalseTest(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);
        Vertex v3 = mock(Vertex.class);
        Vertex v4 = mock(Vertex.class);

        when(v1.getX()).thenReturn(0);
        when(v1.getY()).thenReturn(0);
        when(v2.getX()).thenReturn(3);
        when(v2.getY()).thenReturn(6);

        when(v3.getX()).thenReturn(2);
        when(v3.getY()).thenReturn(1);
        when(v4.getX()).thenReturn(4);
        when(v4.getY()).thenReturn(7);

        Move m1 = new Move(v1,v2);
        Move m2 = new Move(v3,v4);

        assertNotEquals(m1, m2);
        assertNotEquals(m2, m1);
    }

    @Test
    void equalsFalseTest2(){
        Vertex v1 = mock(Vertex.class);
        Vertex v2 = mock(Vertex.class);
        when(v1.getX()).thenReturn(0);
        when(v1.getY()).thenReturn(0);
        when(v2.getX()).thenReturn(3);
        when(v2.getY()).thenReturn(6);

        Move m1 = new Move(v1,v2);
        Object o = new Object();

        assertNotEquals(m1, o);
    }
}
