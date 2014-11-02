package hw_wordnet;

import java.util.Iterator;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class WordNet {
    private SeparateChainingHashST<String, Bag<Integer>> tableNoun;
    private SeparateChainingHashST<Integer, String> tableID;
    private final int V;
    private Digraph digraph;
    private boolean[] marked; // marked[v] = has vertex v been marked?
    private boolean[] onStack; // onStack[v] = is vertex on the stack?
    private int count; // number of vertices with no adjacent vertices
    private SAP sap;
    /**
     * Constructor which takes the name of the two input files
     *
     * @param synsets file name containing a list of noun synsets
     * @param hypernyms file name containing the hypernym relationship
     * @throws java.lang.IllegalArgumentException if input synsets is not DAG
     * @throws java.lang.NullPointerException if any input argument is null
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new java.lang.NullPointerException("inputs cannot be null!");
        }
        V = parseSynsets(synsets);
        digraph = parseHypernyms(hypernyms);
        if (!isRootedDAG(digraph)) {
            throw new IllegalArgumentException("input should be a rooted DAG");
        }
        sap = new SAP(digraph);
    }

    /**
     * Obtain the set of nouns (no duplicates)
     *
     * @return an Iterable
     */
    public Iterable<String> nouns() {
        return tableNoun.keys();
    }

    /**
     * Check if a word is a WordNet noun
     *
     * @param word the word to check
     * @return true if word is a WordNet noun
     * @throws java.lang.NullPointerException if the input argument is null
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new java.lang.NullPointerException("inputs cannot be null!");
        }
        return tableNoun.contains(word);
    }

    /**
     * Get the distance between nounA and nounB.
     * distance is the minimum length of any ancestral path between two nouns
     *
     * @param nounA
     * @param nounB
     * @return the distance between nounA and nounB
     * @throws java.lang.NullPointerException if the input argument is null
     * @throws java.lang.IllegalArgumentException unless both noun arguments
     * are WordNet nouns
     */
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA)) {
            throw new IllegalArgumentException(nounA + " is not a WordNet noun.");
        }
        if (!isNoun(nounB)) {
            throw new IllegalArgumentException(nounB + " is not a WordNet noun.");
        }
        return sap.length(tableNoun.get(nounA), tableNoun.get(nounB));
    }

    /**
     * Get a synset (second field of synsets.txt) that is the common ancestor
     * of nounA and nounB in a shortest ancestral path.
     *
     * @param nounA
     * @param nounB
     * @return a synset
     * @throws java.lang.NullPointerException if the input argument is null
     * @throws java.lang.IllegalArgumentException unless both noun arguments
     * are WordNet nouns
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA)) {
            throw new IllegalArgumentException(nounA
                    + " is not a WordNet noun.");
        }
        if (!isNoun(nounB)) {
            throw new IllegalArgumentException(nounB
                    + " is not a WordNet noun.");
        }
        Iterable<Integer> v = tableNoun.get(nounA);
        Iterable<Integer> w = tableNoun.get(nounB);
        int ancestor = sap.ancestor(v, w);
        if (ancestor == -1) {
            return "";
        } else {
            return tableID.get(ancestor);
        }
    }

    // parse synsets information from the file with name synsets
    private int parseSynsets(String synsets) {
        tableNoun = new SeparateChainingHashST<String, Bag<Integer>>();
        tableID = new SeparateChainingHashST<Integer, String>();
        In in = new In(synsets);
        int lines = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String[] nouns = fields[1].split(" ");
            for (String noun : nouns) {
                if (isNoun(noun)) {
                    tableNoun.get(noun).add(id);
                } else {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    tableNoun.put(noun, bag);
                }
            }
            tableID.put(id, fields[1]);
            lines++;
        }
        return lines;
    }

    // parse hypernyms information from the file with name synsets
    private Digraph parseHypernyms(String hypernyms) {
        Digraph dg = new Digraph(V);
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] fields = in.readLine().split(",");
            int id = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                dg.addEdge(id, Integer.parseInt(fields[i]));
            }
        }
        return dg;
    }

    // check if the digraph G is a rooted directed acyclic graph (DAG)
    private boolean isRootedDAG(Digraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                if (!dfs(G, v)) {
                    return false;
                }
            }
        }
        return true;
    }

    // do DFS from v to check if there is a cycle or there are multiple roots
    private boolean dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        boolean result = true;
        int adj = 0;
        for (int w : G.adj(v)) {
            adj++;
            if (onStack[w]) {
                result = false;
            }
            if (!marked[w]) {
                result = dfs(G, w);
            }
        }
        onStack[v] = false;
        // if v has no neighbours, update count.
        // if updated count > 1, there are more than one roots. return false
        if (adj == 0 && ++count > 1) {
            return false;
        }
        return result;
    }

    // for unit testing of this class
    public static void main(String[] args) {
        String synsets = "src\\tests\\data\\synsets.txt";
        String hypernyms = "src\\tests\\data\\hypernyms.txt";
        WordNet wn = new WordNet(synsets, hypernyms);
        int count = 0;
        for (String noun : wn.nouns()) {
            if (!wn.isNoun(noun)) {
                StdOut.println(noun + " should be a valid noun.");
            }
            count++;
        }
        StdOut.println("There are " + count + " number of nouns.");
    }

}

