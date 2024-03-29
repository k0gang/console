package console;

import java.util.Scanner;

public class Board {
	Scanner sc = new Scanner(System.in);
	public int log;
	
	
	public void run() {
		while(true) {
			printMenu();
			int sel = inputNumber("입력");
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

	
	
	
	
}
