/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	// Testing strategy
	//
	// add: the vertex existes/ not
	// set: weight == 0 / vertices to or from itself/ 
	// 		update weight/ add NOT contained vertices
	// remove: to remove vertices, test the edges connected with it
	// vertices: check (non)existence of (not) added vertices
	// sources/targets: check the weight of setted edges

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		// you may use, change, or remove this test
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// other tests for instance methods of Graph
	@Test
	public void testAdd() {
		String vertex1 = "a";
		String vertex2 = "b";
		String vertex3 = "c";
		Graph<String> graph = emptyInstance();
		assertTrue(graph.add(vertex1));
		assertFalse(graph.add(vertex1));
		assertTrue(graph.add(vertex2));
		assertTrue(graph.add(vertex3));
	}

	@Test
	public void testSet() {
		String vertex1 = "a";
		String vertex2 = "b";
		String vertex3 = "c";
		String vertex4 = "d";
		String vertex5 = "e";

		Graph<String> graph = emptyInstance();
		assertTrue(graph.add(vertex1));
		assertTrue(graph.add(vertex2));
		assertTrue(graph.add(vertex3));

		assertEquals(0, graph.set(vertex1, vertex2, 2));
		assertEquals(2, graph.set(vertex1, vertex2, 3));
		assertEquals(3, graph.set(vertex1, vertex2, 0));
		assertEquals(0, graph.set(vertex1, vertex2, 1));

		assertEquals(0, graph.set(vertex3, vertex3, 1));
		assertEquals(1, graph.set(vertex3, vertex3, 2));
		assertEquals(2, graph.set(vertex3, vertex3, 3));

		assertFalse(graph.vertices().contains(vertex4));
		assertFalse(graph.vertices().contains(vertex5));
		assertEquals(0, graph.set(vertex4, vertex5, 0));
		assertTrue(graph.vertices().contains(vertex4));
		assertTrue(graph.vertices().contains(vertex5));
	}

	@Test
	public void testRemove() {
		String vertex1 = "a";
		String vertex2 = "b";
		String vertex3 = "c";
		String vertex4 = "d";

		Graph<String> graph = emptyInstance();
		assertTrue(graph.add(vertex1));
		assertTrue(graph.add(vertex2));
		assertTrue(graph.add(vertex3));

		assertEquals(0, graph.set(vertex1, vertex2, 1));
		assertEquals(0, graph.set(vertex1, vertex3, 2));
		assertEquals(0, graph.set(vertex2, vertex3, 3));
		assertEquals(0, graph.set(vertex3, vertex1, 4));

		assertTrue(graph.remove(vertex1));
		assertFalse(graph.sources(vertex2).containsKey(vertex1));
		assertFalse(graph.targets(vertex2).containsKey(vertex1));
		assertFalse(graph.sources(vertex3).containsKey(vertex1));
		assertFalse(graph.targets(vertex3).containsKey(vertex1));
		assertFalse(graph.remove(vertex1));
		assertTrue(graph.remove(vertex2));
		assertFalse(graph.remove(vertex4));

		assertFalse(graph.vertices().contains(vertex1));
		assertFalse(graph.vertices().contains(vertex2));
		assertTrue(graph.vertices().contains(vertex3));
		assertFalse(graph.vertices().contains(vertex4));

		assertTrue(graph.targets(vertex1).isEmpty());
		assertTrue(graph.sources(vertex1).isEmpty());
		assertTrue(graph.targets(vertex2).isEmpty());
		assertTrue(graph.sources(vertex2).isEmpty());
	}

	@Test
	public void testVertices() {
		String vertex1 = "a";
		String vertex2 = "b";
		String vertex3 = "c";

		Graph<String> graph = emptyInstance();
		assertTrue(graph.add(vertex1));
		assertTrue(graph.add(vertex2));

		assertTrue(graph.vertices().contains(vertex1));
		assertTrue(graph.vertices().contains(vertex2));
		assertFalse(graph.vertices().contains(vertex3));
		
		assertTrue(graph.add(vertex3));
		assertTrue(graph.vertices().contains(vertex3));
	}

	@Test
	public void testSourcesAndTargets() {
		String vertex1 = "a";
		String vertex2 = "b";
		String vertex3 = "c";
		String vertex4 = "d";

		Graph<String> graph = emptyInstance();
		assertTrue(graph.add(vertex1));
		assertTrue(graph.add(vertex2));
		assertTrue(graph.add(vertex3));
		assertTrue(graph.add(vertex4));

		assertEquals(0, graph.set(vertex1, vertex2, 1));
		assertEquals(0, graph.set(vertex1, vertex3, 2));
		assertEquals(0, graph.set(vertex2, vertex3, 3));
		assertEquals(0, graph.set(vertex2, vertex4, 4));
		assertEquals(0, graph.set(vertex3, vertex1, 5));
		assertEquals(0, graph.set(vertex3, vertex4, 6));
		assertEquals(0, graph.set(vertex4, vertex1, 7));
		assertEquals(0, graph.set(vertex4, vertex3, 8));
		
		Map<String, Integer> sources = graph.sources(vertex1);
		assertEquals(5, sources.get(vertex3).intValue());
		assertEquals(7, sources.get(vertex4).intValue());
		assertFalse(sources.containsKey(vertex1));
		assertFalse(sources.containsKey(vertex2));

		Map<String, Integer> targets = graph.targets(vertex1);
		assertEquals(1, targets.get(vertex2).intValue());
		assertEquals(2, targets.get(vertex3).intValue());
		assertFalse(targets.containsKey(vertex1));
		assertFalse(targets.containsKey(vertex4));

		sources = graph.sources(vertex2);
		assertEquals(1, sources.get(vertex1).intValue());
		assertFalse(sources.containsKey(vertex2));
		assertFalse(sources.containsKey(vertex3));
		assertFalse(sources.containsKey(vertex4));

		targets = graph.targets(vertex2);
		assertEquals(3, targets.get(vertex3).intValue());
		assertEquals(4, targets.get(vertex4).intValue());
		assertFalse(targets.containsKey(vertex1));
		assertFalse(targets.containsKey(vertex2));
	}
}
