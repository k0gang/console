package console;

import java.util.ArrayList;

public class UserManager {
	ArrayList<User> list = new ArrayList<>();
	
	private static UserManager instance = new UserManager();
	
	public static UserManager getInstance(){
		return instance;
	}
	
}
