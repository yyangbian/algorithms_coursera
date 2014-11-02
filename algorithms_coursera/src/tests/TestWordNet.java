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

    @Test
    public void testDistance() {
        assertEquals(5, wn.distance("worm", "bird"));
        assertEquals(23, wn.distance("white_marlin", "mileage"));
        assertEquals(33, wn.distance("Black_Plague", "black_marlin"));
        assertEquals(27, wn.distance("American_water_spaniel", "histology"));
        assertEquals(29, wn.distance("Brown_Swiss", "barrel_roll"));
        assertEquals(7, wn.distance("individual", "edible_fruit"));
    }

    @Test
    public void testSAP() {
        assertEquals("animal animate_being beast brute creature fauna", 
                wn.sap("worm", "bird"));
        assertEquals("physical_entity", wn.sap("individual", "edible_fruit"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordNethWithCycle() {
        String synsets = "src\\tests\\data\\synsets3.txt";
        String hypernyms = "src\\tests\\data\\hypernymsInvalidCycle.txt";
        new WordNet(synsets, hypernyms);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordNethWithTwoRoots() {
        String synsets = "src\\tests\\data\\synsets3.txt";
        String hypernyms = "src\\tests\\data\\hypernymsInvalidTwoRoots.txt";
        new WordNet(synsets, hypernyms);
    }

}
