package console;

import java.util.ArrayList;
import java.util.Scanner;

public class Board extends UserManager {
	Scanner sc = new Scanner(System.in);
	public int log;

	ArrayList<Post> list = new ArrayList<>();

	private final int JOIN = 1;
	private final int LOG_IN = 2;
	private final int LOG_OUT = 3;
	private final int PRINT_BOARD = 4;
	private final int POST = 5;
	private final int MY_POST = 6;

	public void run() {
		while (true) {
			printMenu();
			int sel = inputNumber("입력");
			runMenu(sel);
		}
	}

	private void printMenu() {
		System.out.println("1) 회원가입");
		System.out.println("2) 로그인");
		System.out.println("3) 로그아웃");
		System.out.println("4) 게시판 조회");
		System.out.println("5) 게시글 작성");
		System.out.println("6) 내 게시글 보기");
	}

	private void runMenu(int sel) {
		switch (sel) {
		case JOIN:
			join();
			break;
//		case LOG_IN:
//			logIn();
//			break;
//		case LOG_OUT:
//			logOut();
//			break;
//		case PRINT_BOARD:
//			printBoard();
//			break;
//		case POST:
//			post();
//			break;
//		case MY_POST:
//			myPost();
//			break;
		}
	}
	
	
	private void join() {
		String id = inputString("id");
		if(checkIdDupl(id)) {
			System.err.println("이미 사용중인 id입니다.");
			return;
		}
		String pw = inputString("pw");
		
		User user = new User(id,pw);
		addUser(user);
		
		String message = String.format("%s님 회원가입 완료", id);
		System.out.println(message);
	}
	


	private int inputNumber(String message) {
		int number = -1;
		System.out.print(message + " : ");

		try {
			String input = sc.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자만 입력가능");
		}
		return number;
	}
	
	private String inputString(String message) {
		System.out.print(message + " : ");
		return sc.next();
	}

}
