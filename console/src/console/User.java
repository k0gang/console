package console;

import java.util.ArrayList;

public class User {
	private String id,pw;
	
	ArrayList<Post> list = new ArrayList<>();
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getPw() {
		return this.pw;
	}
	
	public void addPost(Post post) {
		list.add(post);
	}
	
	public void printPost() {
		for(int i=0; i<list.size(); i++) 
			System.out.println(i+1 + ") " + list.get(i));
	}

	public Post findPostByIndex(int index) {
		return list.get(index);
	}
	
	
}
