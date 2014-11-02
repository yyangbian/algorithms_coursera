package hw_wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SAP {

    private Digraph digraph;
    private boolean[] marked; // needed in case input is not DAG

    /**
     * constructor takes a digraph (not necessarily a DAG)
     * @param G a digraph
     * @throws java.lang.NullPointerException if G is null
     */
    public SAP(Digraph G) {
        if (G == null) {
            throw new java.lang.NullPointerException("input cannot be null");
        }
        this.digraph = new Digraph(G);
    }

    /**
     * get length of shortest ancestral path between v and w
     *
     * @param v graph node v
     * @param w graph node w
     * @return lenght of shortest ancestral path; -1 if no such path
     * @throws IndexOutOfBoundsException if v or w is not between 0 and G.V()-1
     */
    public int length(int v, int w) {
        int[] result = getLengthAncestor(v, w);
        return result[0];
    }

    /**
     * Get a common ancestor of v and w that participates in a shortest
     * ancestral path
     *
     * @param v graph node v
     * @param w graph node w
     * @return a common ancestor of v and w that participates in a shortest
     * ancestral path; -1 if no such ancestral path
     * @throws IndexOutOfBoundsException if v or w is not between 0 and G.V()-1
     */
    public int ancestor(int v, int w) {
        int[] result = getLengthAncestor(v, w);
        return result[1];
    }

    /**
     * Get length of shortest ancestral path between any vertex in v and any
     * vertex in w
     *
     * @param v graph node v
     * @param w graph node w
     * @return lenght of shortest ancestral path; -1 if no such path
     * @throws IndexOutOfBoundsException if v or w is not between 0 and G.V()-1
     * @throws NullPointerException if v or w is null
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = getLengthAncestor(v, w);
        return result[0];
        //int distance = Integer.MAX_VALUE;
        //ancestor = -1;
        //while (! q.isEmpty()) {
            //int m = q.dequeue();
            //if (vbfs.hasPathTo(m) && wbfs.hasPathTo(m)) {
                //int d = vbfs.distTo(m) + wbfs.distTo(m);
                //if (distance > d) {
                    //distance = d;
                    //ancestor = m;
                //}
            //}
            //// stop BFS from v or w if the distance is exceeds the lenght of
            //// best ancestral path found so far
            //if (vbfs.distTo(m) < distance ||
                    //wbfs.distTo(m) < distance ) {
                 //for (int n : digraph.adj(m)) {
                    //q.enqueue(n);
                //}
            //}
        //}
        //if (ancestor == -1) {
            //return -1;
        //} else {
            //return distance;
        //}
    }

    /**
     * Get a common ancestor of v and w that participates in a shortest
     * ancestral path
     *
     * @param v graph node v
     * @param w graph node w
     * @return a common ancestor of v and w that participates in a shortest
     * ancestral path; -1 if no such ancestral path
     * @throws IndexOutOfBoundsException if v or w is not between 0 and G.V()-1
     * @throws NullPointerException if v or w is null
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = getLengthAncestor(v, w);
        return result[1];
    }

    private int[] getLengthAncestor(int v, int w) {
        if (v < 0 || v >= digraph.V()) {
            throw new IndexOutOfBoundsException("vertex " + v
                    + " is not between 0 and " + (digraph.V()-1));
        }
        if (w < 0 || w >= digraph.V()) {
            throw new IndexOutOfBoundsException("vertex " + w
                    + " is not between 0 and " + (digraph.V()-1));
        }
        Queue<Integer> q = new Queue<Integer>();
        marked = new boolean[digraph.V()];
        q.enqueue(v);
        q.enqueue(w);
        marked[v] = true;
        marked[w] = true;
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(digraph, w);
        return bfs(q, vbfs, wbfs);
    }

    private int[] getLengthAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) {
            throw new NullPointerException("v cannot be null!");
        }
        if (w == null) {
            throw new NullPointerException("w cannot be null!");
        }
        Queue<Integer> q = new Queue<Integer>();
        marked = new boolean[digraph.V()];
        for (int vi : v) {
            if (vi < 0 || vi >= digraph.V()) {
                throw new IndexOutOfBoundsException("vertex " + vi
                        + " is not between 0 and " + (digraph.V()-1));
            }
            q.enqueue(vi);
            marked[vi] = true;
        }
        for (int wi : w) {
            if (wi < 0 || wi >= digraph.V()) {
                throw new IndexOutOfBoundsException("vertex " + wi
                        + " is not between 0 and " + (digraph.V()-1));
            }
            q.enqueue(wi);
            marked[wi] = true;
        }
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(digraph, w);
        return bfs(q, vbfs, wbfs);
    }

    // return an int array [distance, ancestor]
    private int[] bfs(Queue<Integer> q, BreadthFirstDirectedPaths vbfs,
            BreadthFirstDirectedPaths wbfs) {
        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        while (!q.isEmpty()) {
            int m = q.dequeue();
            if (vbfs.hasPathTo(m) && wbfs.hasPathTo(m)) {
                int d = vbfs.distTo(m) + wbfs.distTo(m);
                if (distance > d) {
                    distance = d;
                    ancestor = m;
                }
            }
            // stop BFS from v or w if the distance is exceeds the lenght of
            // best ancestral path found so far
            // still need to add to queue if the distance to v or w is equal to
            // the lenght of best ancestral path found so far. Otherwise,
            // search will stop if distance to m is 0 from v or w
            if (vbfs.distTo(m) <= distance
                    || wbfs.distTo(m) <= distance) {
                for (int n : digraph.adj(m)) {
                    if (!marked[n]) {
                        marked[n] = true;
                        q.enqueue(n);
                    }
                }
            }
        }
        if (ancestor == -1) {
            return new int[]{-1, -1};
        } else {
            return new int[]{distance, ancestor};
        }
    }


    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
