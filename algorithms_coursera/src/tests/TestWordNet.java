package tests;

import static org.junit.Assert.*;

import hw_wordnet.WordNet;

import org.junit.Before;
import org.junit.Test;

public class TestWordNet {

    private WordNet wn;

    @Before
    public void setUp() throws Exception {
        String synsets = "src\\tests\\data\\synsets.txt";
        String hypernyms = "src\\tests\\data\\hypernyms.txt";
        wn = new WordNet(synsets, hypernyms);
    }

    @Test
    public void testIsNoun() {
        assertTrue(wn.isNoun("'hood"));
        assertTrue(wn.isNoun("Adelgidae"));
        assertTrue(wn.isNoun("family_Adelgidae"));
        assertFalse(wn.isNoun("abcd"));
    }

    @Test
    public void testNouns() {
        int count = 0;
        for (String noun : wn.nouns()) {
            assertTrue(wn.isNoun(noun));
            count++;
        }
        assertTrue(count > 82192);
    }


}
