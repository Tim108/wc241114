package underscore;

public class Classifier {
	
	public String[] extract (String data){
		data.toLowerCase();
		return data.split(" ");
	}
}
