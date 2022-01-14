package P3;

import java.util.LinkedList;
import java.util.List;

public class Person {
	private String name; // Save the name
	private LinkedList<Person> friendList; // Save one's friends

	// Method
	public Person(String str) {
		this.name = str;
		friendList = new LinkedList<Person>();
	}

	// add friend
	public void addFriend(Person newFriend) {
		friendList.add(newFriend);
	}

	// get one's name
	public String name() {
		return name;
	}

	// get one's friend list
	public List<Person> friendList() {
		return this.friendList;
	}
}