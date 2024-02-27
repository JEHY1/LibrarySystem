package Library;

public class Book {

	int id;
	String bookName;
	String author;
	String publisher;
	String locationAt;
	boolean rentable = true;
	User renter;
	
	public Book(int id, String bookName, String author, String publisher, String locationAt) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.author = author.isEmpty() ? "Unknwon" : author;
		this.publisher = publisher.isEmpty() ? "Unknown" : publisher;
		this.locationAt = locationAt.isEmpty() ? "Unknown" : locationAt;
	}
	
	public Book(int id, String bookName) {
		this.id = id;
		this.bookName = bookName;
		this.author = "Unknown";
		this.publisher = "Unkown";
		this.locationAt = "Unknown";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Id : " + this.id + "\t\tBookName : " + this.bookName + "\t\tAuthor : " + this.author + "\t\tPublisher : " + this.publisher + "\t\tRentable : " + this.rentable;
	}
	
	public Book getClone() {
		Book book = new Book(this.id, this.bookName, this.author, this.publisher, this.locationAt);
		book.rentable = this.rentable;
		book.renter = this.renter;
		return book;
	}
}
