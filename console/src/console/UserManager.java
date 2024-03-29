package console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
	ArrayList<User> list = new ArrayList<>();
	
	Map<String, String> userData = new HashMap<String, String>();

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

	public void addUser(User user) {
		list.add(user);
	}
	
}
