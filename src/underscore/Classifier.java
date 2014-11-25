package underscore;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Iterator;


public class Classifier {
	
	public Classifier(){
		fillDB();
	}

	public static final boolean showComments = true;

	/**
	 * 2.1 tokenization This method will strip any punctiation that is mentioned
	 * in the setup file and split the sentence into seperate words.
	 * 
	 * @param data
	 *            the sentence
	 * @return an array of words without punctuation.
	 */
	public static String[] extract(String data) {
		data = Normalizer.normalize(data, Normalizer.Form.NFD);
		return data.replaceAll("[" + SetUp.punctuation + "]", "").toLowerCase()
				.split(" ");
	}

	/**
	 * 2.2.1
	 * 
	 * @param w
	 * @param given
	 * @return
	 */
	public double p(String w, SetUp.GENDER given) {
			double C = given.getFrequency(w);
			double k = 1;
			double V = given.vocLength;
			double N = given.listSize;
			double res = (C+k) / (N+k*V);
			SetUp.comment("P("+w+"|"+given.name()+")=("+C+"+"+k+") / ("+N+"+"+k+"*"+V+")="+res);
			return res;
	}

	/**
	 * 2.2.1
	 * 
	 * @param words
	 * @param given
	 * @return
	 */
	public double overallP(String[] words, SetUp.GENDER given) {
		double res = -1;
		for (String word : words) {
			if (res == -1) {
				res = p(word, given);
			} else {
				res = res * p(word, given);
			}
		}
		SetUp.comment("Total P("+Arrays.toString(words)+"|"+given.name()+")="+res);
		double resLog = Math.log(res) / Math.log(2);
		return resLog;
	}
	
	/**
	 * 2.2.2
	 * @param data
	 * @return
	 */
	public SetUp.GENDER checkGender(String data){
		//prepare data
		String[] words = extract(data);
		SetUp.comment("These are the words we will check: "+Arrays.toString(words));
		//Check first if it is a male
		double pmale = overallP(words, SetUp.GENDER.MALE);
		SetUp.comment("pmale="+pmale);
		
		//Check second if it is a female
		double pfemale = overallP(words, SetUp.GENDER.FEMALE);
		SetUp.comment("pfemale="+pfemale);
		
		//Conclude shit
		if (pmale > pfemale){
			SetUp.comment("It's a manly sentence");
			return SetUp.GENDER.MALE;
		}
		else if (pmale == pfemale){
			SetUp.comment("It's not a manly nor a female sentence");
			return null;
		}
		else {
			SetUp.comment("It's a female sentence");
			return SetUp.GENDER.FEMALE;
		}
	}

	public static void insert(String word, double number, SetUp.GENDER gender) {
		gender.getList().put(word, number);
	}

	public static void fillDB (){
//		insert("male", 5, SetUp.GENDER.MALE);
//		insert("dude", 2, SetUp.GENDER.MALE);
//		insert("herman", 1, SetUp.GENDER.MALE);
//		insert("henk", 1, SetUp.GENDER.MALE);
//		insert("female", 5, SetUp.GENDER.FEMALE);
//		insert("ladies", 2, SetUp.GENDER.FEMALE);
//		insert("love", 5, SetUp.GENDER.FEMALE);
//		insert("i", 15, SetUp.GENDER.FEMALE);
//		insert("i", 10, SetUp.GENDER.MALE);
		SetUp.processTrainingFile("C:\\Users\\Martijn\\Google Drive\\Module 6\\AI - Interactive learner\\blogstrain\\M\\M-train4.txt", SetUp.GENDER.MALE);
		SetUp.processTrainingFile("C:\\Users\\Martijn\\Google Drive\\Module 6\\AI - Interactive learner\\blogstrain\\F\\F-train1.txt", SetUp.GENDER.FEMALE);
		prepareCounts(SetUp.GENDER.FEMALE);prepareCounts(SetUp.GENDER.MALE);
	}
	
	public static void prepareCounts (SetUp.GENDER gender){
		Iterator<Double> it = gender.getList().values().iterator();
		int TotNum = 0;
		while (it.hasNext()){
			TotNum += it.next();
		}
		gender.listSize = TotNum;
		SetUp.comment("ListSize for "+gender.name()+" = "+gender.listSize);
		gender.vocLength = gender.getList().size();
	}
	
	public static void main(String[] args) {
		Classifier c = new Classifier();
		fillDB();
		System.out.println("MALE? "+c.checkGender("Yo Dude, my male henk is fucking herman"));
		System.out.println("FEMALE? "+c.checkGender("my love is sweet like i am"));
		System.out.println("MALE? "+c.checkGender("henk has a dude, herman, i love"));
		System.out.println("???? "+c.checkGender("no idea"));
	}
}
