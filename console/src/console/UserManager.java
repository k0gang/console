package console;

import java.util.ArrayList;

public class UserManager {
	ArrayList<User> list = new ArrayList<>();

	private static UserManager instance = new UserManager();

	public static UserManager getInstance() {
		return instance;
	}
	
	public boolean checkIdDupl(String id) {
		for(User user : list) 
			if(user.getId().equals(id))
				return true;
		return false;
	}

	public boolean checkIdAndPw(String id, String pw) {
		for(User user : list)
			if(user.getId().equals(id) && user.getPw().equals(pw))
				return true;
		return false;
	}
	
	public int findIndexById(String id) {
		for(int i=0; i<list.size(); i++) 
			if(list.get(i).getId().equals(id))
				return i;
		return -1;
	}
	
	public void addUser(User user) {
		list.add(user);
	} 
	
}

