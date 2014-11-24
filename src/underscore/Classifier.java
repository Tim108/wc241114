package underscore;

import java.util.Iterator;


public class Classifier {
	
	public Classifier(){
		fillDB();
	}

	private static final boolean showComments = true;

	/**
	 * 2.1 tokenization This method will strip any punctiation that is mentioned
	 * in the setup file and split the sentence into seperate words.
	 * 
	 * @param data
	 *            the sentence
	 * @return an array of words without punctuation.
	 */
	public String[] extract(String data) {
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
		if (given.getList().containsKey(w)) {
			double freq = given.getFrequentcy(w, given);
			
			double res = freq / given.listSize;
			return res;
		} else {
			return 1;
		}
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
		//Check first if it is a male
		double pmale = overallP(words, SetUp.GENDER.MALE);
		if (showComments ){
			System.out.println("pmale="+pmale);
		}
		//Check second if it is a female
		double pfemale = overallP(words, SetUp.GENDER.FEMALE);
		if (showComments ){
			System.out.println("pfemale="+pfemale);
		}
		//Conclude shit
		if (pmale < pfemale){
			return SetUp.GENDER.MALE;
		}
		else {
			return SetUp.GENDER.FEMALE;
		}
	}

	public static void insert(String word, double number, SetUp.GENDER gender) {
		gender.getList().put(word, number);

		Iterator<Double> it = gender.getList().values().iterator();
		int TotNum = 0;
		while (it.hasNext()){
			TotNum += it.next();
		}
		gender.listSize = TotNum;
		if (showComments){
			System.out.println("ListSize for "+gender.name()+" = "+gender.listSize);
		}
	}

	public static void fillDB (){
		insert("male", 5, SetUp.GENDER.MALE);
		insert("dude", 2, SetUp.GENDER.MALE);
		insert("herman", 1, SetUp.GENDER.MALE);
		insert("henk", 1, SetUp.GENDER.MALE);
		insert("female", 5, SetUp.GENDER.FEMALE);
		insert("ladies", 2, SetUp.GENDER.FEMALE);
		insert("love", 5, SetUp.GENDER.FEMALE);
		insert("i", 15, SetUp.GENDER.FEMALE);
		insert("i", 10, SetUp.GENDER.MALE);
	}
	
	public static void main(String[] args) {
		Classifier c = new Classifier();
		fillDB();
		System.out.println("MALE? "+c.checkGender("Yo Dude, my male henk is fucking herman"));
		System.out.println("FEMALE? "+c.checkGender("my love is sweet like i am"));
		System.out.println("MALE? "+c.checkGender("henk has a dude, herman, i love"));
		System.out.println("???? "+c.checkGender("no idea"));
		// System.out.println(Arrays.toString(c.extract("hoi Ddit is een toiasf98 Q# Q)M FH#QJ Q(# (FQ FQ#*)R Q#FQ(_JFQ)*VH qhroqnr98 q0wjf h9qhfqja fc1h0qiofejq 9wgr39h 9f7gq3r q9 estje?")));
	}
}
