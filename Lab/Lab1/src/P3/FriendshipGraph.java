package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class FriendshipGraph {
	private List<Person> people;

	// method
	public FriendshipGraph () {
		people = new ArrayList<Person>();
	}

	// add a new convex
	public void addVertex (Person newPerson) {
		if (people.contains(newPerson))
			System.out.println("This Vertex "+ newPerson.name()+" Already Existed!");
		else people.add(newPerson);
	}

	// add a new edge between two vertexes
	public void addEdge (Person personA, Person personB) {
		if (!people.contains(personA) || !people.contains(personB)) {
			System.out.println("Not created!");
			System.exit(0);
		}
		// The vertex can't have an edge pointing to itself.
		if (personA.name() == personB.name())
			System.out.println("The vertex " + 
					personA.name() + " can't have an edge pointing to itself");
		// The edge already existed.
		else if (personA.friendList().contains(personB))
			System.out.println("There Already have existed an edge!");
		else personA.addFriend(personB);
	}

	// get the distance between the two vertexes, BFS
	public int getDistance(Person Person1, Person Person2) {
		HashSet<Person> visited = new HashSet<Person>();
		// the distance is 0, when they are the same person
		if (Person1 == Person2)
			return 0;
		int distance = 0, temp;
		visited.add(Person1);
		LinkedList<Person> queue = new LinkedList<Person>();
		queue.addAll(Person1.friendList());
		int levelSize = Person1.friendList().size();
		do {
			temp = 0;
			distance ++;
			for (int i = 0; i < levelSize; i++) {
				if (queue.getFirst() == Person2)
					return distance;
				else if (visited.contains(queue.getFirst()))
					queue.removeFirst();
				else {
					// all the friends of the guy enter the queue
					queue.addAll(queue.getFirst().friendList());
					temp += queue.getFirst().friendList().size();
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
//		graph.addVertex(ross);
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