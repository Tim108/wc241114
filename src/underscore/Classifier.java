package underscore;

import java.util.Arrays;

public class Classifier {
	
	/**
	 * 2.1 tokenization
	 * This method will strip any punctiation that is mentioned in the setup file and split the sentence into seperate words.
	 * @param data the sentence
	 * @return an array of words without punctuation.
	 */
	public String[] extract (String data){
		return data.replaceAll("["+SetUp.punctuation+"]", "").toLowerCase().split(" ");
	}
	
	public static void main (String[] args){
		Classifier c = new Classifier();
		System.out.println(Arrays.toString(c.extract("hoi Ddit is een toiasf98 Q# Q)M FH#QJ Q(# (FQ FQ#*)R Q#FQ(_JFQ)*VH qhroqnr98 q0wjf h9qhfqja fc1h0qiofejq 9wgr39h 9f7gq3r q9 estje?")));
	}
}