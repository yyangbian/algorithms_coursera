package hw_wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class WordNet {
    private SeparateChainingHashST<String, Integer> table;
    private final int V;
    private Digraph digraph;
    /**
     * Constructor which takes the name of the two input files
     *
     * @param synsets file name containing a list of noun synsets
     * @param hypernyms file name containing the hypernym relationship
     * TODO:  throw a java.lang.IllegalArgumentException if the input does not correspond to a rooted DAG.
     */
    public WordNet(String synsets, String hypernyms) {
        table = new SeparateChainingHashST<String, Integer>();
        V = parseSynsets(synsets);
        digraph = new Digraph(V);
        parseHypernyms(hypernyms);
    }

    /**
     * Obtain the set of nouns (no duplicates)
     *
     * @return an Iterable
     */
    public Iterable<String> nouns() {
        return table.keys();
    }

    /**
     * Check if a word is a WordNet noun
     *
     * @param word the word to check
     * @return true if word is a WordNet noun
     */
    public boolean isNoun(String word) {
        return table.contains(word);
    }

    /**
     * Get the distance between nounA and nounB.
     * distance is the minimum length of any ancestral path between two nouns
     *
     * @param nounA
     * @param nounB
     * @return the distance between nounA and nounB
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
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }

    // parse synsets information from the file with name synsets
    private int parseSynsets(String synsets) {
        In in = new In(synsets);
        int count = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String[] nouns = fields[1].split(" ");
            for (String noun : nouns) {
                table.put(noun, id);
            }
            count++;
        }
        return count;
    }

    // parse hypernyms information from the file with name synsets
    private void parseHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] fields = in.readLine().split(",");
            int id = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                digraph.addEdge(id, Integer.parseInt(fields[i]));
            }
        }
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

