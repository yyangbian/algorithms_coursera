package hw_8puzzle;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;


public class Board {
    private int[] tiles;
    private final int N;
    private final int N2; // N^2
    private final int blankRow, blankCol, blankIndex;
    private int mdistance = -1;

    /******************************************************
     * construct a board from an N-by-N array of blocks (where blocks[i][j] =
     * block in row i, column j).
     ******************************************************/
    public Board(int[][] blocks) {
        if (blocks.length != blocks[0].length)
            throw new IllegalArgumentException("Board is of size NxN.");
        N = blocks.length;
        N2 = N * N;
        tiles = new int[N2];
        // initialize tiles and find the blank tile
        int br = -1, bc = -1, bindex = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int index = i * N + j;
                tiles[index] = blocks[i][j];
                if (tiles[index] == 0) {
                    br = i;
                    bc = j;
                    bindex = index;
                }
            }
        }
        this.blankRow = br;
        this.blankCol = bc;
        this.blankIndex = bindex;
        this.mdistance = this.manhattan();
    }

    /******************************************************
     * construct a board from an N^2 array of blocks
     * also set blank tile position and the manhattan distance of the board
     * it will not create a separate copy of the array blocks
     ******************************************************/
    private Board(int[] blocks, int br, int bc, int md) {
        N = (int) Math.sqrt(blocks.length);
        N2 = N * N;
        tiles = blocks;
        this.blankRow = br;
        this.blankCol = bc;
        this.blankIndex = br * N + bc;
        this.mdistance = md;
    }


    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int index = i * N + j;
                if (index != blankIndex && tiles[index] != index + 1)
                    val++;
            }
        }
        return val;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (mdistance < 0) {
            return manhattan(this.tiles);
        } else {
            return mdistance;
        }
    }

    // sum of Manhattan distances between tiles and goal
    private int manhattan(int[] blocks) {
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = blocks[i * N + j];
                if (tile != 0) {
                    int rowDistance = Math.abs((tile - 1) / N - i);
                    int colDistance = Math.abs((tile - 1) % N - j);
                    res += rowDistance + colDistance;
                }
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N2 - 1; i++) {
            if (tiles[i] != i + 1)
                return false;
        }
        if (tiles[N2 - 1] != 0) {
            return false;
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        // swap element in the row next to the row with blank
        int row = (blankRow + 1) % N;
        // swap [row, 0] and [row, 1]`
        int[] newtiles = swap(row * N, row * N + 1);
        return new Board(newtiles, blankRow, blankCol, manhattan(newtiles));
        //return new Board(newtiles);
    }

    /****************************************************
     * does this board equal y?
     ****************************************************/
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null || !(y instanceof Board)) return false;
        Board that = (Board) y;
        for (int i = 0; i < N2; i++) {
            if (tiles[i] != that.tiles[i]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> q = new Stack<Board>();

        int md;
        // case 1: blank not in row 0, swap blankIndex and blankIndex-N
        if (blankRow != 0) {
            int row = (tiles[blankIndex - N] - 1) / N;
            if (row <= blankRow - 1) md = this.mdistance + 1;
            else md = this.mdistance - 1;
            q.push(new Board(swap(blankIndex, blankIndex - N),
                        blankRow - 1, blankCol, md));
            //q.push(new Board(swap(blankIndex, blankIndex - N)));
        }

        // case 2: blank not in row N-1, swap blankIndex and blankIndex+N
        if (blankRow != N - 1) {
            int row = (tiles[blankIndex + N] - 1) / N;
            if (row >= blankRow + 1) md = this.mdistance + 1;
            else md = this.mdistance - 1;
            q.push(new Board(swap(blankIndex, blankIndex + N),
                        blankRow + 1, blankCol, md));
            //q.push(new Board(swap(blankIndex, blankIndex + N)));
        }

        // case 3: blank not in column 0, swap blankIndex and blankIndex-1
        if (blankCol != 0) {
            int column = (tiles[blankIndex - 1] - 1) % N;
            if (column <= blankCol - 1) md = this.mdistance + 1;
            else md = this.mdistance - 1;
            q.push(new Board(swap(blankIndex, blankIndex - 1),
                        blankRow, blankCol - 1, md));
            //q.push(new Board(swap(blankIndex, blankIndex - 1)));
        }

        // case 4: blank not in column N-1, swap blankIndex and blankIndex+1
        if (blankCol != N - 1) {
            int column = (tiles[blankIndex + 1] - 1) % N;
            if (column >= blankCol + 1) md = this.mdistance + 1;
            else md = this.mdistance - 1;
            q.push(new Board(swap(blankIndex, blankIndex + 1),
                       blankRow, blankCol + 1, md));
            //q.push(new Board(swap(blankIndex, blankIndex + 1)));
        }

        return q;
    }

    // string representation of board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i * N + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // return a copy of this.tiles but with i and j swapped
    private int[] swap(int i, int j) {
        int[] newtiles = new int[N2];
        System.arraycopy(tiles, 0, newtiles, 0, N2);
        newtiles[i] = tiles[j];
        newtiles[j] = tiles[i];
        return newtiles;
    }

    // test client solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial);
        System.out.println("isGoal test: " + initial.isGoal());
        System.out.println("twin is: \n" + initial.twin());
        for (Board b : initial.neighbors())
            System.out.println(b);
        System.out.println("Hamming method: " + initial.hamming());
        System.out.println("Manhattan method: " + initial.manhattan());
    }
}

