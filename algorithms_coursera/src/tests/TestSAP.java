package tests;

import static org.junit.Assert.*;

import hw_wordnet.SAP;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;

public class TestSAP {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testDigraphAmbiguousSingleLengthAncestor() {
        In in = new In("src\\tests\\data\\digraph-ambiguous-ancestor.txt");
        SAP sap_amb = new SAP(new Digraph(in));
        assertEquals(1, sap_amb.length(0, 10));
        assertEquals(1, sap_amb.length(10, 0));
        assertEquals(10, sap_amb.ancestor(0, 10));
        assertEquals(10, sap_amb.ancestor(10, 0));
        assertEquals(5, sap_amb.length(0, 6));
        assertEquals(5, sap_amb.length(6, 0));
        int ancestor = sap_amb.ancestor(0, 6);
        assertTrue( ancestor == 2 || ancestor == 7);
        ancestor = sap_amb.ancestor(6, 0);
        assertTrue( ancestor == 2 || ancestor == 7);
    }

    @Test
    public void testDigraph1SingleLengthAncestor() {
        In in = new In("src\\tests\\data\\digraph1.txt");
        SAP sap = new SAP(new Digraph(in));
        assertTrue(sap.length(9, 12) == 3 && sap.ancestor(9, 12) == 5);
        assertTrue(sap.length(9, 8) == 4 && sap.ancestor(9, 8) == 1);
        assertTrue(sap.length(9, 8) == sap.length(8, 9));
        assertTrue(sap.length(7, 12) == sap.length(12, 7));
    }

    @Test
    public void testDigraph2SingleLengthAncestor() {
        In in = new In("src\\tests\\data\\digraph2.txt");
        SAP sap = new SAP(new Digraph(in));
        assertTrue(sap.length(2, 3) == 1 && sap.ancestor(2, 3) == 3);
        assertTrue(sap.length(2, 5) == 3 && (sap.ancestor(2, 5) == 0 ||
                    sap.ancestor(2,5) == 5));
        assertTrue(sap.length(1, 5) == 2 && sap.ancestor(1, 5) == 0);
        assertTrue(sap.length(2, 3) == sap.length(3, 2));
        assertTrue(sap.length(2, 5) == sap.length(5, 2));
        assertTrue(sap.length(1, 5) == sap.length(5, 1));
    }

    @Test
    public void testDigraph3SingleLengthAncestor() {
        // digraph3 is not a DAG
        In in = new In("src\\tests\\data\\digraph3.txt");
        SAP sap = new SAP(new Digraph(in));
        assertTrue(sap.length(2, 5) == 3 && sap.ancestor(2, 5) == 2);
        assertTrue(sap.length(1, 5) == 2 && sap.ancestor(1, 5) == 1);
        assertTrue(sap.length(2, 5) == sap.length(5, 2));
        assertTrue(sap.length(1, 5) == sap.length(5, 1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLengthAncestorException() {
        In in = new In("src\\tests\\data\\digraph1.txt");
        Digraph d = new Digraph(in);
        SAP sap = new SAP(d);
        int v = d.V();
        sap.length(v, v-1);
    }

}
