package hw_wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SAP {

    private Digraph digraph;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     * TODO: verify G is a DAG
     * @param G a digraph
     * @throws TODO
     */
    public SAP(Digraph G) {
        this.digraph = new Digraph(G);
    }

    /**
     * get length of shortest ancestral path between v and w
     *
     * @param v graph node v
     * @param w graph node w
     * @return lenght of shortest ancestral path; -1 if no such path
     * @throws TODO
     */
    public int length(int v, int w) {
        return -1;
    }

    /**
     * Get a common ancestor of v and w that participates in a shortest
     * ancestral path
     *
     * @param v graph node v
     * @param w graph node w
     * @return a common ancestor of v and w that participates in a shortest
     * ancestral path; -1 if no such ancestral path
     * @throws TODO
     */
    public int ancestor(int v, int w) {
        return -1;
    }

    /**
     * Get length of shortest ancestral path between any vertex in v and any
     * vertex in w
     *
     * @param v graph node v
     * @param w graph node w
     * @return lenght of shortest ancestral path; -1 if no such path
     * @throws TODO
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    /**
     * Get a common ancestor of v and w that participates in a shortest
     * ancestral path
     *
     * @param v graph node v
     * @param w graph node w
     * @return a common ancestor of v and w that participates in a shortest
     * ancestral path; -1 if no such ancestral path
     * @throws TODO
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
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
