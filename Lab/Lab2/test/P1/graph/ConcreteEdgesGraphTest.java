/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /* 
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // check whether the toString's return is as expected
    
    // tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToString() {
        String vertex1 = "a";
		String vertex2 = "b";
		String vertex3 = "c";

		Graph<String> graph = emptyInstance();
		assertTrue(graph.add(vertex1));
		assertTrue(graph.add(vertex2));
		assertTrue(graph.add(vertex3));

        assertEquals(0, graph.set(vertex1, vertex2, 1));
		assertEquals(0, graph.set(vertex1, vertex3, 2));
		assertEquals(0, graph.set(vertex2, vertex3, 3));

        StringBuffer expString = new StringBuffer();
        expString.append("Vertices: \\a\\ \\b\\ \\c\\\n");
        expString.append("Edge <a, b>  Weight:1\n");
        expString.append("Edge <a, c>  Weight:2\n");
        expString.append("Edge <b, c>  Weight:3\n");
        String expected = expString.toString();
        assertEquals(expected, graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    // sources/ targets: to or from themselves/ different
    // weight: negative/ nonegative

    // tests for operations of Edge
    @Test
    public void testEdge() {
        Edge<String> edge = null;
        Edge<String> edge2 = null;
        Edge<String> edge3 = null;
        // negative weight of a edge
        try {
            edge = new Edge<String>("a", "b", -1);
            fail("not catch weight < 0 error");
        } catch (AssertionError error) {}
        // add some normal edges
        edge = new Edge<String>("a", "b", 1);
        edge2 = new Edge<String>("c", "d", 2);
        edge3 = new Edge<String>("e", "e", 3);
        // check the source, target and weight of a edge
        assertEquals("a", edge.source());
        assertEquals("b", edge.target());
        assertEquals(1, edge.weight());
        assertEquals("c", edge2.source());
        assertEquals("d", edge2.target());
        assertEquals(2, edge2.weight());
        assertEquals("e", edge3.target());
        assertEquals(3, edge3.weight());
        // check the existence of some edges
        assertTrue(edge.haveSuchEdge("a", "b"));
        assertTrue(edge2.haveSuchEdge("c", "d"));
        assertFalse(edge.haveSuchEdge("a", "c"));
        assertFalse(edge.haveSuchEdge("a", "d"));
        assertFalse(edge.haveSuchEdge("a", "a"));
        assertFalse(edge.haveSuchEdge("b", "a"));
        assertFalse(edge.haveSuchEdge("b", "b"));
        assertFalse(edge.haveSuchEdge("b", "c"));
        assertFalse(edge.haveSuchEdge("b", "d"));
    }
}
