package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hw_8puzzle.Board;

import org.junit.Before;
import org.junit.Test;

public class TestBoard {
    private int[][] tiles3a, tiles3b, tiles3c;
    private int[][] tiles4, tiles4a;
    private int[][] solution;

    @Before
    public void setUp() throws Exception {
        solution = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        tiles3a = new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        tiles3b = new int[][] { { 6, 1, 3 }, { 4, 2, 5 }, { 8, 7, 0 } };
        tiles3c = new int[][] { { 6, 1, 3 }, { 4, 0, 5 }, { 8, 7, 2 } };
        tiles4 = new int[4][];
        tiles4[0] = new int[] { 0, 15, 14, 13 };
        tiles4[1] = new int[] { 12, 11, 10, 9 };
        tiles4[2] = new int[] { 8, 7, 6, 5 };
        tiles4[3] = new int[] { 4, 3, 2, 1 };
        tiles4a = new int[4][];
        tiles4a[0] = new int[] {6, 9, 7, 4};
        tiles4a[1] = new int[] {2, 5, 10, 8};
        tiles4a[2] = new int[] {3, 11, 1, 0};
        tiles4a[3] = new int[] {13, 14, 15, 2};
    }

    @Test
    public void testDimension() {
        Board b = new Board(tiles3a);
        assertEquals(3, b.dimension());
        b = new Board(tiles4);
        assertEquals(4, b.dimension());
    }

    @Test
    public void testHamming() {
        Board b = new Board(tiles3a);
        assertEquals("toles3a hamming is 4", 4, b.hamming());
        b = new Board(tiles3b);
        assertEquals("toles3a hamming is 6", 6, b.hamming());
        b = new Board(tiles4);
        assertEquals("toles4 hamming is 15", 15, b.hamming());
    }

    @Test
    public void testManhattan() {
        Board b = new Board(tiles3a);
        assertEquals("toles3a manhattan is 4", 4, b.manhattan());
        b = new Board(tiles3b);
        assertEquals("toles3a manhattan is 8", 8, b.manhattan());
        b = new Board(tiles4);
        assertEquals("tiles4 manhattan is 58", 58, b.manhattan());
    }

    @Test
    public void testIsGoal() {
        Board b = new Board(solution);
        assertTrue("Failue - solution is not solution", b.isGoal());
        b = new Board(tiles3a);
        assertFalse("Failue - tile3a is not solution", b.isGoal());
    }

    @Test
    public void testTwin() {
        Board b = new Board(tiles3a);
        Board twin = b.twin();
        int[][] tileTwin = new int[][] { { 0, 1, 3 }, { 2, 4, 5 }, { 7, 8, 6 } };
        Board result = new Board(tileTwin);
        assertTrue(twin.equals(result));
    }

    @Test
    public void testEqualsObject() {
        Board a = new Board(tiles3a);
        Board b = new Board(tiles3a);
        assertTrue("Board a is equal to b", a.equals(b));
        assertTrue("Board b is equal to a", b.equals(a));
        b = new Board(tiles3b);
        assertFalse("Board a is not equal to b", a.equals(b));
        assertFalse("Board b is not equal to a", b.equals(a));
        b = null;
        assertFalse("Board a is not equal to null", a.equals(b));
        String c = "test";
        assertFalse("Board a is not equal to a string", a.equals(c));
    }

    @Test
    public void testNeighbors() {
        Board b = new Board(tiles3a);
        int[][] tmp1 = new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] tmp2 = new int[][]{{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};
        Board[] neighbors = new Board[]{new Board(tmp1), new Board(tmp2)};
        int i = 0;
        for (Board n : b.neighbors()) {
            assertEquals(neighbors[i], n);
            i++;
        }
    }

    @Test
    public void testToString() {
        Board a = new Board(solution);
        String result = "3\n 1  2  3 \n 4  5  6 \n 7  8  0 \n";
        assertEquals(result, a.toString());
    }

}
