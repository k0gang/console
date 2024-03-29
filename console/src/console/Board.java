package console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board extends UserManager {
	private Scanner sc = new Scanner(System.in);
	public int log = -1;

	ArrayList<Post> list = new ArrayList<>();

	Map<User, Post> board = new HashMap<User, Post>();

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
		if(this.log != -1)
			printLogStatus();
		System.out.println("1) 회원가입");
		System.out.println("2) 로그인");
		System.out.println("3) 로그아웃");
		System.out.println("4) 게시판 조회");
		System.out.println("5) 게시글 작성");
		System.out.println("6) 내 게시글 보기");
	}
	
	private void printLogStatus() {
		String id = findIdByIndex(log);
		
		String message = String.format("[%s님 로그인중]",id);
		System.out.println(message);
	}

	private void runMenu(int sel) {
		switch (sel) {
		case JOIN:
			join();
			break;
		case LOG_IN:
			logIn();
			break;
		case LOG_OUT:
			logOut();
			break;
//		case PRINT_BOARD:
//			printBoard();
//			break;
		case POST:
			post();
			break;
//		case MY_POST:
//			myPost();
//			break;
		}
	}
	
	
	private void join() {
		if(isLogin()) {
			System.err.println("로그아웃 후 이용 가능합니다.");
			return;
		}
		
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
	
	private void logIn() {
		if(isLogin()) {
			System.err.println("이미 로그인 중입니다.");
			return;
		}
		
		String id = inputString("id");
		String pw = inputString("pw");
		
		if(!checkIdAndPw(id,pw)) {
			System.err.println("회원정보가 일치하지 않습니다");
			return;
		}
		
		this.log = findIndexById(id);
		
		String message = String.format("%s님 로그인 완료", id);
		System.out.println(message);
	}
	
	private void logOut() {
		if(!isLogin()) {
			System.err.println("로그아웃 상태입니다.");
			return;
		}
		
		this.log = -1;
		
		System.out.println("로그아웃 되었습니다.");
	}
	
	private void post() {
		if(!isLogin()) {
			System.err.println("로그인 후 이용 가능합니다.");
			return;
		}
		
		String title = inputString("title");
		String text = inputString("text");
		
		Post post = new Post(title, text);
		
		User user = findUserByIndex(log);
		
		user.addPost(post);
		board.put(user, post);
		
		System.out.println("게시글 작성 완료");
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
	
	private boolean isLogin() {
		return this.log == -1 ? false : true;
	}

}
