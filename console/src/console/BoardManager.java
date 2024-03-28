package console;

import java.util.ArrayList;

public class BoardManager {
	ArrayList<Board> list = new ArrayList<>();
	
	private static BoardManager instance = new BoardManager();
	
	public static BoardManager getInstance() {
		return instance;
	}
	
}
