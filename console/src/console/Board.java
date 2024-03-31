package console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board extends UserManager {
	private Scanner sc = new Scanner(System.in);
	public int log = -1;

	ArrayList<Post> list = new ArrayList<>();

	Map<User, ArrayList<Post>> userBoard = new HashMap<User, ArrayList<Post>>();

	private final int JOIN = 1;
	private final int LOG_IN = 2;
	private final int LOG_OUT = 3;
	private final int PRINT_BOARD = 4;
	private final int POST = 5;
	private final int MY_POST = 6;

	private final int DELETE = 1;
	private final int UPDATE = 2;
	private final int SEARCH = 3;

	private final int UPDATE_TITLE = 1;
	private final int UPDATE_TEXT = 2;

	public void run() {
		while (true) {
			printMenu();
			int sel = inputNumber("입력");
			runMenu(sel);
		}
	}

	private void printMenu() {
		if (this.log != -1)
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

		String message = String.format("[%s님 로그인중]", id);
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
		case PRINT_BOARD:
			printBoard();
			break;
		case POST:
			post();
			break;
		case MY_POST:
			myPost();
			break;
		}
	}

	private void join() {
		if (isLogin()) {
			System.err.println("로그아웃 후 이용 가능합니다.");
			return;
		}

		String id = inputString("id");
		if (checkIdDupl(id)) {
			System.err.println("이미 사용중인 id입니다.");
			return;
		}
		String pw = inputString("pw");

		User user = new User(id, pw);
		addUser(user);
		userBoard.put(user, user.list);

		String message = String.format("%s님 회원가입 완료", id);
		System.out.println(message);
	}

	private void logIn() {
		if (isLogin()) {
			System.err.println("이미 로그인 중입니다.");
			return;
		}

		String id = inputString("id");
		String pw = inputString("pw");

		if (!checkIdAndPw(id, pw)) {
			System.err.println("회원정보가 일치하지 않습니다");
			return;
		}

		this.log = findIndexById(id);

		String message = String.format("%s님 로그인 완료", id);
		System.out.println(message);
	}

	private void logOut() {
		if (!isLogin()) {
			System.err.println("로그아웃 상태입니다.");
			return;
		}

		this.log = -1;

		System.out.println("로그아웃 되었습니다.");
	}

	private void printBoard() {
		for (int i = 0; i < list.size(); i++) {
			Post post = list.get(i);
			System.out.println(i + 1 + ") " + post);
		}

		System.out.println("0) 뒤로가기");
		int sel = inputNumber("선택");

		if (sel < 0 || sel > list.size()) {
			System.err.println("?");
			return;
		}

		if (sel == 0)
			return;

		printText(sel);
	}

	private void printText(int sel) {
		Post post = list.get(sel - 1);

		String text = post.getText();
		System.out.println(text);
	}

	private void post() {
		if (!isLogin()) {
			System.err.println("로그인 후 이용 가능합니다.");
			return;
		}

		String title = inputString("title");
		String text = inputString("text");

		Post post = new Post(title, text);

		User user = findUserByIndex(log);

		list.add(post);
		user.addPost(post);
		userBoard.put(user, user.list);

		System.out.println("게시글 작성 완료");
	}

	private void myPost() {
		if (!isLogin()) {
			System.err.println("로그인 후 이용가능합니다.");
			return;
		}

		User user = findUserByIndex(log);

		user.printPost();

		printSubMenu();
		int sel = inputNumber("입력");

		runSubMenu(sel);
	}

	private void printSubMenu() {
		System.out.println("1) 삭제");
		System.out.println("2) 수정");
		System.out.println("3) 조회");
	}

	private void runSubMenu(int sel) {
		switch (sel) {
		case DELETE:
			deletePost();
			break;
		case UPDATE:
			updatePost();
			break;
		case SEARCH:
			viewPost();
			break;
		}
	}

	private void deletePost() {
		User user = findUserByIndex(log);

		int sel = inputNumber("삭제할 게시글 번호 입력");

		if (!checkSel(sel))
			return;

		Post post = user.findPostByIndex(sel - 1);

		user.list.remove(post);
		userBoard.put(user, user.list);
		list.remove(post);

		System.out.println("게시글 삭제 완료");
	}

	private void updatePost() {
		User user = findUserByIndex(log);

		int sel = inputNumber("수정할 게시글 번호 입력");

		if (!checkSel(sel))
			return;

		runUpdateSubMenu(sel);

	}

	private void runUpdateSubMenu(int index) {
		System.out.println("1)제목 수정");
		System.out.println("2)내용 수정");

		int sel = inputNumber("입력");

		if (sel == UPDATE_TITLE)
			updateTitle(index);
		else if (sel == UPDATE_TEXT)
			updateText(index);
	}

	private void updateTitle(int index) {
		User user = findUserByIndex(log);

		String title = inputString("제목");

		Post post = user.findPostByIndex(index - 1);

		String text = post.getText();

		Post updatePost = new Post(title, text);

		user.list.set(index - 1, updatePost);
		userBoard.put(user, user.list);

		int idx = list.indexOf(post);
		list.set(idx, updatePost);

		System.out.println("제목 수정 완료");
	}

	private void updateText(int index) {
		User user = findUserByIndex(log);

		String text = inputString("내용");

		Post post = user.findPostByIndex(index - 1);

		String title = post.getTitle();

		Post updatePost = new Post(title, text);

		user.list.set(index - 1, updatePost);
		userBoard.put(user, user.list);

		int idx = list.indexOf(post);
		list.set(idx, updatePost);

		System.out.println("내용 수정 완료");
	}

	private void viewPost() {
		User user = findUserByIndex(log);

		int sel = inputNumber("조회할 게시글 번호 입력");

		if (!checkSel(sel))
			return;

		Post post = user.findPostByIndex(sel - 1);

		String text = post.getText();

		System.out.println(text);
	}

	private boolean checkSel(int sel) {
		User user = findUserByIndex(log);

		if (sel < 1 || sel > user.list.size()) {
			System.err.println("?");
			return false;
		}

		return true;
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
