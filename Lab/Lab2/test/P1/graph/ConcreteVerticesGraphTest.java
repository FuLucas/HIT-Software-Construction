/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    // check whether the toString's return is as expected
    
    // tests for ConcreteVerticesGraph.toString()
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
		assertEquals(0, graph.set(vertex2, vertex3, 3));

        StringBuffer expString = new StringBuffer();
        expString.append("Vertices: \\a\\ \\b\\ \\c\\\n");
        expString.append("Edge <a, b>  Weight:1\n");
        
        expString.append("Edge <b, c>  Weight:3\n");
        String expected = expString.toString();
        assertEquals(expected, graph.toString());
    }

    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    // check the name of the vertex
    // edges: check sources/targets, check negetive weight
    // toString(): same or not
    
    // tests for operations of Vertex
    @Test
    public void testVertex() {
        Vertex<String> v1 = new Vertex<String>("a");
        Vertex<String> v2 = new Vertex<String>("b");
        // name is the same or not
        assertEquals("a", v1.name());
        v2.addSourceEdges(v2, 1);
        v2.addTargetEdges(v2, 1);
        v1.addSourceEdges(v1, 2);
        v1.addTargetEdges(v1, 2);
        // ERROR CONDITIONS
        // negetive weight
        try {
            v1.addSourceEdges(v2, -1);
            fail("not catch weight < 0 error");
        } catch (AssertionError error) {}
        try {
            v1.addSourceEdges(v2, 0);
            fail("not catch weight == 0 error");
        } catch (AssertionError error) {}

        // check the sources and targets of the vertex
        // whether their weights are the same as expected.
        Map<Vertex<String>, Integer> sources = new HashMap<>();
        Map<Vertex<String>, Integer> targets = new HashMap<>();
        sources = v1.sources();
        targets = v1.targets();
        assertEquals(2, sources.get(v1).intValue());
        assertEquals(2, targets.get(v1).intValue());

        // check the toString() method of Vertex
        StringBuffer temp = new StringBuffer();
        temp.append("Vertex: \\a\\\n");
        temp.append("Edges whose source is a.\n");
        temp.append("Edge <a, a>  Weight:2\n");
        temp.append("Edges whose target is a.\n");
        temp.append("Edge <a, a>  Weight:2\n");

        temp.append("Vertex: \\b\\\n");
        temp.append("Edges whose source is b.\n");
        temp.append("Edge <b, b>  Weight:1\n");
        temp.append("Edges whose target is b.\n");
        temp.append("Edge <b, b>  Weight:1\n");

        String expected = temp.toString();
        assertEquals(expected, v1.toString() + v2.toString());   
    }
}
