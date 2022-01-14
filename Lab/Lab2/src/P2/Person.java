package P2;

public class Person {
	private final String name;
	// Abstraction function:
	// AF(name) = the name of the person

	// Safety from rep exposure:
	// all fields are final and private

	// constructor
	public Person(String str) {
		this.name = str;
	}

	// metheds
	public String name() {
		return name;
	}
}