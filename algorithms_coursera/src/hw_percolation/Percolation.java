package hw_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean [][] siteOpen; // 2-D array tracking open/close sites
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBVNode;
    private final int N;
    private final int TOP_VIRTUAL_NODE;
    private final int BOTTOM_VIRTUAL_NODE;

// create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        this.N = N;
        TOP_VIRTUAL_NODE = N * N;
        BOTTOM_VIRTUAL_NODE = TOP_VIRTUAL_NODE + 1;
        siteOpen = new boolean [N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufNoBVNode = new WeightedQuickUnionUF(N * N + 1);
    }

// open site (row i, column j) if it is not already
    public void open(int i, int j) {
        indexOutOfBounds(i, j);
        if (!isOpen(i, j)) {
            siteOpen[i-1][j-1] = true;
            updateConnection(i, j);
        }
    }

// is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        indexOutOfBounds(i, j);
        return siteOpen[i-1][j-1];
    }

// is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        indexOutOfBounds(i, j);
        return ufNoBVNode.connected((i - 1) * N + (j - 1), TOP_VIRTUAL_NODE);
    }

// does the system percolate?
    public boolean percolates() {
        return uf.connected(TOP_VIRTUAL_NODE, BOTTOM_VIRTUAL_NODE);
    }

// connect a new open site to its neighboring open sites
    private void updateConnection(int i, int j) {
        int index = (i - 1) * N + (j - 1);
        if (i > 1 && isOpen(i - 1, j)) {  // connect to row above
            uf.union(index, index - N);
            ufNoBVNode.union(index, index - N);
        }
        if (i == N) // bottom row connect to bottom virtual node
            uf.union(index, BOTTOM_VIRTUAL_NODE);
        if (i < N && isOpen(i + 1, j)) { // connect to row below
            uf.union(index, index + N);
            ufNoBVNode.union(index, index + N);
        }
        // top row connect to top virtual node
        if (i == 1) {
            uf.union(index, TOP_VIRTUAL_NODE);
            ufNoBVNode.union(index, TOP_VIRTUAL_NODE);
        }
        if (j > 1 && isOpen(i, j - 1)) { // connect to column on the left
            uf.union(index, index - 1);
            ufNoBVNode.union(index, index - 1);
        }
        if (j < N && isOpen(i, j + 1)) { // connect to column on the right
            uf.union(index, index + 1);
            ufNoBVNode.union(index, index + 1);
        }
    }

// validate if the given index (i, j) is out of bound
    private void indexOutOfBounds(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("row index j out of bounds");
    }
}