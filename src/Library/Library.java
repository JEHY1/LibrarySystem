package Library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Library {
	
	TreeMap<Integer, Book> books = new TreeMap<>();
	TreeMap<Integer, User> users = new TreeMap<>();
	
	public void addBook(int _bookId, String _bookName, String _publisher, String _author, String _locationAt) { //books에 인자값을을 이용하여 객체성성후 추가
		books.put(_bookId, new Book(_bookId, _bookName, _publisher, _author, _locationAt));
	}
	
	public void simpleAddBook(int _bookId, String _bookName) { //books에 인자값을을 이용하여 객체성성후 추가
		books.put(_bookId, new Book(_bookId, _bookName));
	}
	
	public Book searchBook(int _bookId) { //books의 키 값을 이용하여 책 검색후 객체 반환 
		return books.get(_bookId);
	}
	
	public Book[] searchBook(String _bookName) {//이름으로 책 검색
		Object[] searchedBook2 = books.values().stream().filter(o1 -> _bookName.equals(o1.bookName)).toArray();//_bookName과 같은 책이름을 가진 모든 객체를 Object배열로 반환
		int size = searchedBook2.length;
		
		if(size == 0){
			return new Book[] {};
		}
		
		Book[] searchedBooks = new Book[size];
		int index = 0;

		for(Object book : searchedBook2) { //Object배열을 Book배열로 변환
			if(book instanceof Book) {
				searchedBooks[index++] = (Book)book;
			}
		}
		return searchedBooks;
	}
	
	public Book deleteBook(int _bookId) {//bookId 로 책 삭제 후 삭제된 책 객체 리턴
		Book book = books.get(_bookId);
		System.out.println(book);
		if(!book.rentable) { //책을 빌릴수 있는 상태인지 (누군가 빌렸다면 책을 삭제못하도록)
			System.out.println("Fail : this book is rented");
			return null;
		}
		
		if(Librarys.getYesOrNo()) { //책을 빌릴 수 있을 경우 삭제할지 안할지를 Console 입력을 통해 확인
			Book deletedbook = books.remove(_bookId); //books에서 해당 책 삭제
			return deletedbook;
		}
		return null;
	}
	
	public Book[] deleteBook(String _bookName) {//BookName으로 책 삭제 후 삭제된 책들을 배열로 리턴
		ArrayList<Book> deleteBookList = new ArrayList<>();
		Book[] searchedBooks = searchBook(_bookName);
		if(searchedBooks.length == 0) { //해당 이름의 책이 없을경우 
			System.out.println("Not Found Book Name");
			return new Book[] {}; //빈 Book배열 반환
		}
		System.out.println("==============================================serched book================================================");
		
		for(Book book : searchedBooks) { //해당 이름의 책 모두 console 출력 
			if(book != null) {
				System.out.println(book);
			}
		}
		
		System.out.println();
		for(Book book : searchedBooks) {//각각의 책에 대해서 삭제 여무를 물음
			System.out.println(book);
			if(Librarys.getYesOrNo()) {
				if(book.rentable) { //누군가 빌린 상태인 책은 삭제 불가
					deleteBookList.add(books.remove(book.id)); //삭제후 반환을 위한 리스트에 삭제된 책 추가
				}
				else {
					System.out.println("Fail : this book is rented");
				}
			}
		}
		
		
		System.out.println("==============================deleted List=======================================");
		for(Book book : deleteBookList) { //최종적으로 삭제된 책들 Console 출력
			System.out.println(book);
		}
		
		if(deleteBookList.size() == 0) { //삭제된 책이 없을경우 빈 Book 배열 반환
			return new Book[] {};
		}
		else {//삭제된 책들을 ArrayList -> 배열로 변환 deleteBookList.toArray(deletedBooks); 와 같음
			Book[] deletedBooks = new Book[deleteBookList.size()];
			deleteBookList.toArray(deletedBooks);
			int index = 0;
			for(Object obj : deleteBookList.toArray()) {
				if(obj instanceof Book) {
					Book book = (Book)obj;
					deletedBooks[index++] = book;
				}
			}
			return deletedBooks;
		}
	}
	
	public Book rentBook(int _userId, Book _rentBook) { //책 빌리기 UserId에 해당하는 유저 _rentBook 객체 빌림 //빌린 책을 반환
		User rentUser = users.get(_userId); //유저 객체 가져옴
		
		if(rentUser.rentedBooks.size() == 10) { //10권 제한
			System.out.println("cant more rant");
			return null;
		}
		
		if(_rentBook.rentable == false) { //이미 해당 책을 누군가 빌린 경우
			System.out.println("already rented");
			return null;
		}
		
		System.out.println(_rentBook);
		System.out.println("rent?");
		if(Librarys.getYesOrNo()) { //빌릴건지 안빌릴건지 확인
			rentUser.rentedBooks.add(_rentBook); //유저 객체의 빌린 책 List에 추가
			_rentBook.rentable = false; //책 정보 수정 (빌릴 수 없는 상태로 변환)
			_rentBook.renter = rentUser; //책 정보 수정 (빌린 사람 객체 저장)
			
			System.out.println("빌린 사람 : " + _rentBook.renter);
			System.out.println("result");
			System.out.println(_rentBook);
		}
		return _rentBook;
	}
	
	public Book[] rentBook(int _userId, String _bookName) { //책 이름으로 책 빌리기
		User rentUser = users.get(_userId); //빌리는 사람
		ArrayList<Book> rentBooks = new ArrayList<>(); //빌릴 책들 List
		
		if(searchBook(_bookName) == null) { //원하는 책 이름에 해당하는 책이 없을 경우
			System.out.println("can not found book");
			return new Book[] {};
		}
		Book[] rentableBooks = searchBook(_bookName);
		
		if(rentUser.rentedBooks.size() == 10) { //10권 제한 검사
			System.out.println("cant more rant");
			return new Book[] {};
		}
		System.out.println("======================================serched books========================================");
		
		for(Book book : rentableBooks) { //검색된 책들 Console 출력
			System.out.println(book);
		}
		
		for(Book rentBook : rentableBooks) { //각 책에 대해 빌릴건지 확인
			System.out.println(rentBook);
			if(rentUser.rentedBooks.size() == 10) { //10권 제한 검사
				System.out.println("Can Not Rent More");
				break;
			}
			
			if(Librarys.getYesOrNo()) {
				rentUser.rentedBooks.add(rentBook);
				rentBook.rentable = false;
				rentBook.renter = rentUser;
				rentBooks.add(rentBook);
			}
		}
		
		if(rentBooks.isEmpty()) { //빌린 책이 없을 경우 빈 배열 리턴
			return new Book[] {};
		}
		else {//ArrayList -> 배열 후 배열 리턴(빌린 책들)
			Book[] rentBook = new Book[rentBooks.size()];
			int index = 0;
			for(Object obj : rentBooks) {
				if(obj instanceof Book) {
					Book book = (Book)obj;
					rentBook[index++] = book;
				}
			}
			return rentBook;
		}
	}
	
	public Book[] returnBook(int _userId) { // UserId 의 유저가 빌린 책 반납
		if(!users.containsKey(_userId)) { //존재하는 유저인지 확인, 없빈배
			System.out.println("cannot found user");
			return new Book[] {};
		}
		
		User user = users.get(_userId);
		System.out.println("======================================================userid " + _userId + " rented book==================================================");
		
		for(Book rentedBook : user.rentedBooks) { //해당 유저가 빌린 모든 책 정보 console 줄력
			System.out.println(rentedBook);
		}
		
		ArrayList<Book> returnList = new ArrayList<>(); //반납할 책들 List
		for(Book rentedBook : user.rentedBooks) { //빌린 모든 책에 대해 각각 반납여부 물음
			System.out.println(rentedBook);
			System.out.println("return ? (Y(y) / any)");
			if(Librarys.getYesOrNo()) { //반납하면 책 정보 수정
				rentedBook.rentable = true;
				rentedBook.renter = null;
				returnList.add(rentedBook);
			}
		}
		user.rentedBooks.removeAll(returnList); //현재 유저가 빌린 책들 List에서 반납할 책들을 모두 삭제
		
		if(returnList.isEmpty()) { //없빈배
			return new Book[] {};
		}
		else { //ArrayList -> 배열 후 반환
			Book[] returnBooks = new Book[returnList.size()];
			int index = 0;
			for(Book book : returnList) {
				returnBooks[index++] = book;
			}
			return returnBooks;
		}
	}
	
	public void showBookList() { //모든 책 정보 출력
		for(Book book : books.values()) {
			System.out.print(book);
			if(!book.rentable) {
				System.out.println("\t" + book.renter);
			}
			System.out.println();
		}
	}
	
	public Book[] updateBook() { //책 정보 수정
		System.out.print("book Id : ");
		int bookId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+")); //업데이트할 책의 Id를 입력 받음
		Book book = books.get(bookId);
		Book[] beforeAfter = new Book[2];
		if(book == null) { //해당 Id의 책이 없으면 빈배열 리턴
			System.out.println("cannot found Book Id");
			return new Book[] {};
		}
		
		System.out.println("========================================before update===========================================");
		System.out.println(book); //업데이트 전 확인용 출력
		System.out.println();
		System.out.println("new Book Id : ");
		String newBookIdStringType =  Librarys.getConsoleInput("[0-9]*"); //새로운 BookId입력받음
		int newBookId = newBookIdStringType.equals("") ? bookId : Integer.parseInt(newBookIdStringType); //공백의 경우 기존 Id를 그대로 쓰는것으로 인식
		
		if(newBookId == bookId) { //bookId 는 기존 번호를 그대로 쓸 경우
			beforeAfter[0] = book.getClone(); //기존 Book의 정보를 저장하는 객체 복제
			System.out.println("new Book Name : ");
			book.bookName = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			System.out.println("new Author : ");
			book.author = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			System.out.println("new Publisher : ");
			book.publisher = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			System.out.println(("new LocationAt : "));
			book.locationAt = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			beforeAfter[1] = book; //정보 수정된 객체
			return beforeAfter; //beforeAfter[0] = 수정전 정보를 가진 객체 beforeAfter[1] = 수정된 객체
		}
		else { //bookId가 변경될 경우
			if(books.containsKey(newBookId)) { //변경하고 싶은 bookId가 이미 사용중인지 확인
				System.out.println("already used Book Id");
				return new Book[] {}; 
			}
			System.out.println("new Book Name : ");
			String newBookName = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			System.out.println("new Author : ");
			String newAuthor = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			System.out.println("new Publisher : ");
			String newPublisher = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			System.out.println(("new LocationAt : "));
			String newLocationAt = Librarys.getConsoleInput("[a-zA-z가-힣]*");
			
			
			Book newBook = new Book(newBookId, newBookName, newAuthor, newPublisher, newLocationAt); //새로운 변경된 정보의 객체 생성해놓기
			if(!books.get(bookId).rentable) { //업데이트 하고 싶은 책을 누군가 빌린 상태일경우
				User user = book.renter; //해당 책을 빌린 유저 객체
				user.rentedBooks.remove(book); //원래 객체 제거
				user.rentedBooks.add(newBook); //새로 업데이트된 객체를 추가
				newBook.renter = user; //업데이트된 책에 빌린사람 추가
				newBook.rentable = false; //빌릴수 없는 상태로
			}
			beforeAfter[0] = book; //변경 전
			beforeAfter[1] = newBook; //변경 후 
			books.remove(bookId); //원래 책 삭제
			books.put(newBookId, newBook); //새로운 책 추가
			return beforeAfter;
		}
	}
}
