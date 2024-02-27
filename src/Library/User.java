package Library;

import java.util.ArrayList;

public class User {

	ArrayList<Book> rentedBooks = new ArrayList<>();
	
	String name;
	int id;
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id : " + this.id + "\t\tname : " + this.name + "\t\trentCount : " + rentedBooks.size();
	}
}
