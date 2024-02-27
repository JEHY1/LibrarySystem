package Library;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Librarys {

	static Scanner sc = new Scanner(System.in);
	private Librarys() {}

	public static String getConsoleInput (String _regix) { //_regix(정규표현식)에 해당하는 console 입력이 들어올 때까지 반복 후 해당하면 해당 값을 String 타입 리턴
		System.out.print("input : ");
		String str = sc.nextLine();
		while(!str.matches(_regix)) {
			System.out.println("re input : ");{
				str = sc.nextLine();
			}
		}
		return str;
	}
	
	public static boolean getYesOrNo() { //console 입력을 한번 받고 입력값이 "Y" 또는 "y" 이면 true 아니면 false 리턴
		System.out.println("intput (Y / any)");
		return sc.nextLine().matches("[Yy]") ? true : false;
	}
	
	public static void updateLog(ArrayList<String> logs) throws Exception { //file 경로에 logs 의 문자열들을 추가
		File file = new File("C:\\Users\\admin\\Desktop\\workspace\\Library\\src\\Library\\LibrarySystemLog.txt");
		BufferedOutputStream bfs = new BufferedOutputStream(new FileOutputStream(file, true));
		if(!logs.isEmpty()) {
			for(String log : logs) {
				bfs.write(log.getBytes());
			}
		}
		bfs.flush();
		bfs.close();
	}
}
