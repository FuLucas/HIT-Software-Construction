package P2;

import static org.junit.Assert.*;
import org.junit.Test;

public class FriendshipGraphTest {

	@Test
	public void GraphTest1() {
		FriendshipGraph graph = new FriendshipGraph();

		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");

		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);

		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);

		assertEquals(1, graph.getDistance(rachel, ross));
		assertEquals(2, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
	}


	@Test
	public void GrpahTest2() {
		FriendshipGraph graph = new FriendshipGraph();

		Person a = new Person("A");
		Person b = new Person("B");
		Person c = new Person("C");
		Person d = new Person("D");
		Person e = new Person("E");
		Person f = new Person("F");
		Person g = new Person("G");
		Person h = new Person("H");
		Person i = new Person("I");
		Person j = new Person("J");

		assertTrue(graph.addVertex(a));
		assertTrue(graph.addVertex(b));
		assertTrue(graph.addVertex(c));
		assertTrue(graph.addVertex(d));
		assertTrue(graph.addVertex(e));
		assertTrue(graph.addVertex(f));
		assertTrue(graph.addVertex(g));
		assertTrue(graph.addVertex(h));
		assertTrue(graph.addVertex(i));
		assertTrue(graph.addVertex(j));

		assertEquals(0, graph.addEdge(a, d));
		assertEquals(1, graph.addEdge(a, d));
		assertEquals(-1, graph.addEdge(a, a));
		assertEquals(0, graph.addEdge(a, e));
		assertEquals(0, graph.addEdge(b, a));
		assertEquals(0, graph.addEdge(b, e));
		assertEquals(0, graph.addEdge(c, f));
		assertEquals(0, graph.addEdge(d, c));
		assertEquals(0, graph.addEdge(g, h));
		assertEquals(0, graph.addEdge(g, i));
		assertEquals(0, graph.addEdge(i, h));
		assertEquals(0, graph.addEdge(j ,i));

		assertEquals(3, graph.getDistance(a, f));
		assertEquals(-1, graph.getDistance(a, b));
		assertEquals(-1, graph.getDistance(e, f));
		assertEquals(2, graph.getDistance(d, f));
		assertEquals(-1, graph.getDistance(a, j));
		assertEquals(0, graph.getDistance(i, i));
		assertEquals(-1, graph.getDistance(g, j));
		assertEquals(1, graph.getDistance(i, h));
	}
}