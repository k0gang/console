package console;

public class Post {
	private String title, text;
	
	public Post(String title, String text) {
		this.title = title;
		this.text = text;
	}
	
	
	
	@Override
	public String toString() {
		return String.format("%s",title);
	}
}