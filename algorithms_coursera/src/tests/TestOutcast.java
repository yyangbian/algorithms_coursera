package tests;

import static org.junit.Assert.*;

import hw_wordnet.Outcast;
import hw_wordnet.WordNet;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.In;

public class TestOutcast {
    private WordNet wordnet;
    private Outcast outcast;

    @Before
    public void setUp() throws Exception {
        String synsets = "src\\tests\\data\\synsets.txt";
        String hypernyms = "src\\tests\\data\\hypernyms.txt";
        wordnet = new WordNet(synsets, hypernyms);
        outcast = new Outcast(wordnet);
    }

    private String[] readNouns(String fileName) {
        In in = new In(fileName);
        return in.readAllStrings();
    }

    @Test
    public void testOutcast1() {
        String[] nouns = readNouns("src\\tests\\data\\outcast5.txt");
        assertEquals("table", outcast.outcast(nouns));
    }

    @Test
    public void testOutcast2() {
        String[] nouns = readNouns("src\\tests\\data\\outcast8.txt");
        assertEquals("bed", outcast.outcast(nouns));
    }

    @Test
    public void testOutcast3() {
        String[] nouns = readNouns("src\\tests\\data\\outcast11.txt");
        assertEquals("potato", outcast.outcast(nouns));
    }
}
