package console;

//콘솔 게시판 만들기
//ㄴ Map 활용
//ㄴ User CRUD
//ㄴ Board CRUD
//	 ㄴ 글 작성자만 권한이 있다.

public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		board.run();
	}
}
