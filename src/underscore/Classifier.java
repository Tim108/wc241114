package underscore;

public class Classifier {
	
	public String[] extract (String data){
		data.toLowerCase();
		data.replaceAll(SetUp.punctuation, "");
		return data.split(" ");
	}
}
