package P3;

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

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
		graph.addVertex(i);
		graph.addVertex(j);

		graph.addEdge(a, d);
		graph.addEdge(a, e);
		graph.addEdge(b, a);
		graph.addEdge(b, e);
		graph.addEdge(c, f);
		graph.addEdge(d, c);
		graph.addEdge(g, h);
		graph.addEdge(g, i);
		graph.addEdge(i, h);
		graph.addEdge(j, i);


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