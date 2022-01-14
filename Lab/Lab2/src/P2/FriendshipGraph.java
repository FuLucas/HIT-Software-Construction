package P2;

import P1.graph.*;
import java.util.HashSet;
import java.util.LinkedList;

public class FriendshipGraph {
	private final Graph<Person> graph;

	public FriendshipGraph () {
		graph = Graph.empty();
	}

	// add a new convex
	public boolean addVertex (Person newPerson) {
		return graph.add(newPerson);
	}

	// add a new edge between two vertexes
	public int addEdge (Person personA, Person personB) {
		if (personA.name().equals(personB.name())) return -1;
		int prevWeight;
		prevWeight = graph.set(personA, personB, 1);
		return prevWeight;
	}

	// get the distance between the two vertexes, BFS
	public int getDistance(Person personA, Person personB) {
		// nobody is a friend of oneself
		HashSet<Person> visited = new HashSet<Person>();
		// the distance is 0, when they are the same person
		if (personA == personB)
			return 0;
		int temp, distance = 0;
		visited.add(personA);
		LinkedList<Person> queue = new LinkedList<Person>();
		queue.addAll(graph.targets(personA).keySet());
		int levelSize = queue.size();
		do {
			temp = 0;
			distance ++;
			for (int i = 0; i < levelSize; i++) {
				if (queue.getFirst() == personB)
					return distance;
				else if (visited.contains(queue.getFirst()))
					queue.removeFirst();
				else {
					// all the friends of the guy enter the queue
					queue.addAll(graph.targets(queue.getFirst()).keySet());
					temp += graph.targets(queue.getFirst()).size();
					// set the guy as visited
					visited.add(queue.getFirst());
					queue.removeFirst();
				}
			}
			// all the reachable are visited, but person 2 not found
			if (temp == 0) return -1;
			levelSize = temp;
		} while(true);
	}
	
	public static void main (String args[]) {
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
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
	}
}