package Library;

import java.util.ArrayList;
import java.util.Arrays;

public class SystemEx {
	public static void main(String[] args)throws Exception {

		Library myLibrary = new Library();
		
		//
		
		
		///
		while (true) {
			System.out.println("menu : 1.addBook, 2.searchBook, 3.deleteBook, 4.rentBook, 5.returnBook, 6.showBookList, 7.signUp, 8.showUsers, 9.updateBook, 0.exit ");
			int bookId;
			String bookName;
			int userId;
			String userName;
			Book book;
			//이하 로그 작업용
			Book deletedBook; 
			Book[] deletedBooks; 
			Book rentBook;
			Book[] rentBooks;
			Book[] returnBooks;
			Book[] updateBeforeAfter;
			ArrayList<String> logs = new ArrayList<>();
			
			//Librarys.getConsoleInput(String _regix) 는 매게변수에 해당하는 정규표현식을 만족하는 console 입력값을 받을때까지 사용자에게 콘솔 입력을 요청하고 정규표현식을 만족하는 값을 리턴하는 메서드 입니다.
			switch (Librarys.getConsoleInput("[0-9]")) { //메뉴 선택
			case "1"://책 추가
				System.out.println("menu : 1.simpleAdd, 2.allInfoAdd");
				switch (Librarys.getConsoleInput("[12]")) { //하위 메뉴 선택
				case "1": //bookId 와 bookName만 입력받아서 추가(나머지 Unknown);
					System.out.println("bookId  ");
					bookId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+")); //bookId는 0 또는 자연수
					logs.add("simple add Book");
					if (myLibrary.books.containsKey(bookId)) { //myLibrary에 존재하는 bookId 인지 확인 (bookId는 중복 불가)
						System.out.println("BookId " + bookId + "is already Used Book Id");
						logs.add("\tfail : bookId " + bookId + " is already Used Book Id");
						logs.add("\ttime : " + System.currentTimeMillis() + "\n");
						Librarys.updateLog(logs);
						break;
					}
					System.out.println("bookName  "); 
					bookName = Librarys.getConsoleInput("[a-zA-Z가-힣0-9]+"); //책 이름 한글 영어 숫자 조합, 1문자 이상
					myLibrary.simpleAddBook(bookId, bookName); 
					logs.add("[BookId : " + bookId + "BookName : " + bookName +"]");
					logs.add("\ttime : " + System.currentTimeMillis() + "\n");
					Librarys.updateLog(logs);
					break;
					
				case "2": //모든 정보를 입력받아서 저장 (bookId와 bookName은 필수 이며, 나머지는 미입력시 Unknown)
					System.out.println("bookId  "); 
					bookId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
					logs.add("All INfO Add Book ");
					if (myLibrary.books.containsKey(bookId)) { //bookId 존재하는지 확인
						logs.add("Fail : bookId " + bookId + "is already Used Book Id");
						logs.add("Time : " + System.currentTimeMillis() + "\n");
						Librarys.updateLog(logs);
						System.out.println("already Used Book Id");
						break;
					}
					System.out.println("bookName : ");
					String bookName2 = Librarys.getConsoleInput("[0-9a-zA-Z가-힣]+");
					System.out.println("bookAuthor : ");
					String bookAuthor = Librarys.getConsoleInput("[a-zA-Z가-힣]*");
					System.out.println("bookPublisher : ");
					String bookPublisher = Librarys.getConsoleInput("[0-9a-zA-Z가-힣]*");
					System.out.println("bookLocationAt : ");
					String bookLocationAt = Librarys.getConsoleInput("[0-9a-zA-Z가-힣]*");
					logs.add("[BookId : " + bookId + " bookName : " + bookName2 + "bookPublisher : " + bookPublisher + " Book Author : " + bookAuthor + "BookLocationAt : " + bookLocationAt + "]");
					logs.add("Time : " + System.currentTimeMillis() + "\n");
					Librarys.updateLog(logs);
					myLibrary.addBook(bookId, bookName2, bookPublisher, bookAuthor, bookLocationAt);
					break;
				}
				break;

			case "2"://책 검색
				System.out.println("menu : 1.search id , 2.search BookName");
				switch (Librarys.getConsoleInput("[12]")) {
				case "1": //book id를 이용한 검색
					System.out.println("book id");
					bookId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
					book = myLibrary.searchBook(bookId);
					if (book != null) { //검색된 경우 console에 책 정보 출력
						System.out.println(book);
					}
					logs.add("SerchBook Use BookId[Book Id : " + bookId + "]");
					logs.add("Time : " + System.currentTimeMillis() + "\n");
					Librarys.updateLog(logs);
					break;
					
				case "2": //bookName을 이용한 검색
					System.out.println("book Name  ");
					bookName = Librarys.getConsoleInput("[0-9a-zA-z가-힣]+");
					Book[] searchedBooks = myLibrary.searchBook(bookName); //책 이름은 중복 가능함으로 myLibrary.books 에서 책 이름이 검색된 책 이름과 같은 모든 책 객체를 배열로 리턴

					if (searchedBooks.length != 0) { // 검색 결과 있는 경우
						System.out.println("========================found Book List================================");
						for (Book searchedBook : searchedBooks) { //모든 책 정보 출력
							System.out.println(searchedBook);
						}
					}
					else {//검색 결과 없는 경우
						System.out.println("notFoundBook");
					}
					logs.add("SerchBook Use BookName[BookName : " + bookName + "]");
					logs.add("Time : " + System.currentTimeMillis() + "\n");
					Librarys.updateLog(logs);
					break;
				}
				break;
				
			case "3": //책 삭제
				System.out.println("menu : 1. use BookId, 2.use BookName");
				switch (Librarys.getConsoleInput("[12]")) {
				case "1": //bookId로 삭제
					bookId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
					logs.add("Delete Book Use BookId[BookId : " + bookId + "]");
					if (!myLibrary.books.containsKey(bookId)) {
						System.out.println("not found");
						logs.add("\tFail : Not Found BookId");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
						Librarys.updateLog(logs);
						break;
					}
					deletedBook = myLibrary.deleteBook(bookId);
					if(deletedBook == null) {
						logs.add("\tDelete Nothing");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
						
					}
					else {
						logs.add("\tDeleted Book Info : [" + deletedBook + "]");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					}
					Librarys.updateLog(logs);
					break;
				case "2": //bookname으로 삭제
					System.out.println("book Name");
					bookName = Librarys.getConsoleInput("[0-9a-zA-z가-힣]+");
					deletedBooks = myLibrary.deleteBook(bookName);
					if(deletedBooks.length == 0) {
						logs.add("\tDelete Nothing");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					}
					else {
						logs.add("\tDeleted Book Info : [" + Arrays.toString(deletedBooks) + "]");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					}
					Librarys.updateLog(logs);
					System.out.println("end");
					break;
				}
				break;
			case "4": //책 대여 
				System.out.println("user Id : ");
				userId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
				logs.add("Rent Book User[" + userId + "]");
				if (!myLibrary.users.containsKey(userId)) { //userId 가 없으면 해당 작업 끝내기
					logs.add("\tFail : Can Not Found User");
					logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					Librarys.updateLog(logs);
					System.out.println("can not found User");
					break;
				}
				System.out.println("menu : 1.use BookId, 2.use BookName");

				switch (Librarys.getConsoleInput("[12]")) {
				case "1": //BookId 를 입력받아서 해당 책을 대여
					System.out.println("bookId");
					bookId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
					logs.add(" Use BookId : " + bookId);
					if (!myLibrary.books.containsKey(bookId)) { //입력받은 BookId 에 해당하는 책이 존재하는지 확인 후 없으면 해당 작업을 종료
						logs.add("\tFail : Can Not Found BookId");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
						Librarys.updateLog(logs);
						System.out.println("can not found bookId");
						break;
					}
					rentBook = myLibrary.rentBook(userId, myLibrary.searchBook(bookId)); //빌리기 프로세스 실행후 빌린 책 객체 가져오기
					if(rentBook == null) {//책을 안빌렸을 경우
						logs.add("\tRent Nothing");
						logs.add("\tRent : " + System.currentTimeMillis() + "\n");
						
					}
					else {//책을 빌렸을 경우
						logs.add("\tRent Book Info : [" + rentBook + "]");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					}
					Librarys.updateLog(logs);
					break;
					
				case "2":// BookName을 입력받아서 책 대여 //검색한 책 이름과 같은 모든 책에 대해 대여 여부를 물어봄 
					System.out.println("Book Name");
					bookName = Librarys.getConsoleInput("[0-9a-zA-z가-힣]+");
					logs.add(" Use BookName : " + bookName);
					rentBooks = myLibrary.rentBook(userId, bookName);
					if(rentBooks.length == 0) {//아무것도 안빌렸을 경우
						logs.add("\tRent Nothing");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					}
					else {//빌린책이 있을경우
						logs.add("\tRent Book Info : [" + Arrays.toString(rentBooks) + "]");
						logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					}
					Librarys.updateLog(logs);
					break;
				}
				break;

			case "5": //책 반납하기
				System.out.println("user Id : ");
				userId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
				logs.add("Return Book User Id : " + userId);
				returnBooks = myLibrary.returnBook(userId); //user id에 해당하는 유저가 빌린 모든 책에 대해 반납여부를 물어봄 -> 반납한 책을 배열로 반환
				if(returnBooks.length == 0) { //반납한 책이 업을 경우
					logs.add("Return Anything");
					logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				}
				else { //반납한 책이 있을 경우
					logs.add(" Return BookInfo : [" + Arrays.toString(returnBooks) +"]");
					logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				}
				Librarys.updateLog(logs);
				break;
				
			case "6": //현재 추가된 모든 book 객체를 console에 출력
				myLibrary.showBookList();
				logs.add("Show Book List");
				logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				Librarys.updateLog(logs);
				break;
				
			case "7": //사용자 객채 생성및 추가
				System.out.println("user Id : ");
				userId = Integer.parseInt(Librarys.getConsoleInput("[0-9]+"));
				logs.add("SignUp\tUser Id : " + userId);
				if (myLibrary.users.containsKey(userId)) { //이미 존재하는 userId는 추가 할 수 없음 -> 작업 종료
					logs.add("\tFail : Already Used UserId");
					logs.add("\tTime : " + System.currentTimeMillis() + "\n");
					System.out.println("already used UserId");
					Librarys.updateLog(logs);
					break;
				}
				System.out.println("userName : ");
				userName = Librarys.getConsoleInput(".+");
				myLibrary.users.put(userId, new User(userId, userName)); //Usre 객체 생성해서 추가
				
				logs.add("\tNew User Info[Id : " + userId + " Name : " + userName +"]");
				logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				Librarys.updateLog(logs);
				break;
				
			case "8": //모든 유저 정보 conlsole 출력
				for (User user : myLibrary.users.values()) {
					System.out.println(user);
					if (!user.rentedBooks.isEmpty()) { //각 유저마다 빌린 책이 있으면 빌린 책에 대한 정보 같이 출력
						System.out.println(user.rentedBooks);
					}
				}
				logs.add("Show User List");
				logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				Librarys.updateLog(logs);
				break;
				
			case "9": //책 정보 업데이트 
				updateBeforeAfter = myLibrary.updateBook();
				logs.add("Update Book");
				if(updateBeforeAfter.length == 0) {
					logs.add(" Fail");
					logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				}
				else {
					logs.add("[" + updateBeforeAfter[0] + "] -> ");
					logs.add("[" + updateBeforeAfter[1] + "]" );
					logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				}
				Librarys.updateLog(logs);
				break;
				
			case "0":
				logs.add("System Exit");
				logs.add("\tTime : " + System.currentTimeMillis() + "\n");
				Librarys.updateLog(logs);
				System.exit(0);
			}

		}

	}
}
