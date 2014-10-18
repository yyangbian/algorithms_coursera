package hw_8puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Solver {
    //private final Board goal;
    private boolean solvable;
    private SearchNode solutionNode;

    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final int mdistance;
        private final Board board;
        private final int moves;
        private final SearchNode prevSearchNode;

        public SearchNode(Board board, SearchNode prevSearchNode) {
            this.board = board;
            this.prevSearchNode = prevSearchNode;
            if (prevSearchNode == null)
                moves = 0;
            else
                moves = prevSearchNode.moves + 1;
            mdistance = board.manhattan();
            priority = mdistance + moves;
        }

        public int compareTo(SearchNode that) {
            if (priority < that.priority) {
                return -1;
            } else if (priority > that.priority) {
                return 1;
            } else {
                if (mdistance < that.mdistance) {
                    return -1;
                } else if (mdistance > that.mdistance) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode n = new SearchNode(initial, null);
        SearchNode ntwin = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        pq.insert(n);
        pqTwin.insert(ntwin);
        while (true) {
            n = pq.delMin();
            ntwin = pqTwin.delMin();
            //if (n.board.equals(goal) || ntwin.board.equals(goal))
            if (n.board.isGoal() || ntwin.board.isGoal())
                break;
            for (Board b : n.board.neighbors()) {
                if (n.prevSearchNode == null
                        || !b.equals(n.prevSearchNode.board))
                    pq.insert(new SearchNode(b, n));
            }
            for (Board b : ntwin.board.neighbors()) {
                if (ntwin.prevSearchNode == null
                    || !b.equals(ntwin.prevSearchNode.board))
                    pqTwin.insert(new SearchNode(b, ntwin));
            }
        }
        // if (n.board.equals(goal)) {
        if (n.board.isGoal()) {
            solutionNode = n;
            solvable = true;
        } else {
            solutionNode = null;
            solvable = false;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable())
            return solutionNode.moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> solution = new Stack<Board>();
        solution.push(solutionNode.board);
        SearchNode n = solutionNode;
        while (n.prevSearchNode != null) {
            solution.push(n.prevSearchNode.board);
            n = n.prevSearchNode;
        }
        return solution;
    }

    // test client solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        // String arg = "inputfile\\puzzle04.txt";
        // In in = new In(arg);
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        Stopwatch sw = new Stopwatch();
        // solve the puzzle
        Solver solver = new Solver(initial);
        double elapsedTime = sw.elapsedTime();

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = "
                    + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        StdOut.println("Elapsed time: " + elapsedTime + " sec\n");
    }
}