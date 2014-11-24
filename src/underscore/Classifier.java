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
	
	/**
	 * 2.2.1
	 * @param w
	 * @param given
	 * @return
	 */
	public double p (String w, SetUp.GENDER given){
		double freq = given.getFrequentcy(w, given);
		int TotNum = given.getList().size();
		return freq/TotNum;
	}
	
	/**
	 * 2.2.1
	 * @param words
	 * @param given
	 * @return
	 */
	public double overallP (String[] words, SetUp.GENDER given){
		double res = -1;
		for(String word: words){
			if (res == -1){
				res = p(word, given);
			}
			else {
				res=res*p(word, given);
			}
		}
		return Math.log(res)/Math.log(2);
	}
	
	public static void insert (String word, int number, SetUp.GENDER gender){
		gender.getList().put(word, number);
	}
	
	public static void main (String[] args){
		Classifier c = new Classifier();
		insert("male", 5, SetUp.GENDER.MALE);
		insert("dude", 2, SetUp.GENDER.MALE);
		insert("herman", 1, SetUp.GENDER.MALE);
		insert("henk", 1, SetUp.GENDER.MALE);
		insert("female", 5, SetUp.GENDER.FEMALE);
		insert("ladies", 2, SetUp.GENDER.FEMALE);
		insert("love", 5, SetUp.GENDER.FEMALE);
		insert("i", 15, SetUp.GENDER.FEMALE);
		System.out.println(c.overallP(c.extract("Yo Dude, my male henk is fucking herman"),SetUp.GENDER.MALE));
		System.out.println(Arrays.toString(c.extract("hoi Ddit is een toiasf98 Q# Q)M FH#QJ Q(# (FQ FQ#*)R Q#FQ(_JFQ)*VH qhroqnr98 q0wjf h9qhfqja fc1h0qiofejq 9wgr39h 9f7gq3r q9 estje?")));
	}
}
