package underscore;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import underscore.SetUp.GENDER;

public class Classifier {

	public Classifier() {
	}

	public static boolean showComments = true;
	private LinkedList<GENDER> gen = new LinkedList<GENDER>();
	private String wordsPrefix = "Words: ";

	/**
	 * 2.1 tokenization This method will strip any punctiation that is mentioned
	 * in the setup file and split the sentence into seperate words.
	 * 
	 * @param data
	 *            the sentence
	 * @return an array of sentences without punctuation.
	 */
	public static String[] extract(String data) {
		data = Normalizer.normalize(data, Normalizer.Form.NFD);

		//SetUp.comment("The data: "+data);
		String[] returnData =  data.replaceAll("[" + SetUp.punctuation + "]", "").toLowerCase()
				.trim().replaceAll("\\s+", " ").split("\\.");
		//SetUp.comment(Arrays.toString(returnData));
		return returnData;
	}

	public static String[] senToWords(String sentence) {
		String[] returnData = sentence.trim().replaceAll("\\s+", " ").split(" ");
		//SetUp.comment(Arrays.toString(returnData));
		return returnData;
	}
	
	public static String[] extractToWords(String data) {
		data = Normalizer.normalize(data, Normalizer.Form.NFD);
		String[] returnData = data.replaceAll("[" + SetUp.punctuation + ".]", "").toLowerCase()
				.trim().replace("[", "").replace("]", "").replaceAll("\\s+", " ").split(" ");
		//SetUp.comment(Arrays.toString(returnData));
		return returnData;
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
		double k = 0.000000000000001;
		double V = given.vocLength+given.other().vocLength;
		double N = given.listSize+given.other().listSize;
		double res = (C + k) / (N + k * V);
		SetUp.comment("P(" + w + "|" + given.name() + ")=(" + C + "+" + k
				+ ") / (" + N + "+" + k + "*" + V + ")=" + res);
		return res;
	}

	/**
	 * 2.2.1
	 * 
	 * @param words SINGLE WORDS!! 
	 * @param given
	 * @return
	 */
	public double overallP(String[] words, SetUp.GENDER given) {
		comment("Get the P of >"+words+"<");
		try {
		for (String word: words){
			if (word.contains(" ")){
				throw new Exception("THESE ARE NOT WORDS! This word contains: "+word+". This is one of the "+words.length+" words");
			}
		}
		double Pword = -1;
		double PwordOther = -1;
		double Pgiven = given.vocLength;
		for (String word : words) {
			if (Pword == -1) {
				Pword = p(word, given);
			} else {
				Pword = Pword * p(word, given);
			}
		}
		for (String word : words) {
			if (PwordOther == -1) {
				PwordOther = p(word, given.other());
			} else {
				PwordOther = Pword * p(word, given.other());
			}
		}
		double res = Pword*Pgiven/(Pword*Pgiven+PwordOther*(1-Pgiven));
		SetUp.comment("Total P(" + given.name() + "|" + Arrays.toString(words)
				+ ")=" + res);
		double resLog = Math.log(res) / Math.log(2);
		return resLog;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 2.2.2
	 * 
	 * @param data
	 * @return
	 */
	public SetUp.GENDER checkGender(String data) {
		// Fill a blog as data
		comment("Check what gender wrote the data");
		// Is the data extracted into words? (does not contain spaces)
		if (!isWords(data)) {
			// It is not words, but it can still be a whole blog or sentences
			comment("It is not words, but a blog or sentence: "+data);
			// it is a blog, extract the data
			String[] sentences = extract(data);
			comment("The data is extracted: This is the new data: "+Arrays.toString(sentences));
			// Split the sentences into words
			for (String sentence : sentences) {
				String[] words = senToWords(sentence);
				comment("The words in the first sentence are: "+Arrays.toString(words));
				gen.add(checkGender(wordsPrefix +Arrays.toString(words)));
			}

			// Now we know the gender of each sentence, calculate an average
			if (majorityOf(gen, GENDER.MALE)) {
				return GENDER.MALE;
			} else {
				return GENDER.FEMALE;
			}
		} else {

			String[] words = getWords(data);
			SetUp.comment("These are the words we will check: "
					+ Arrays.toString(words));
			// Check first if it is a male
			double pmale = overallP(words, SetUp.GENDER.MALE);
			SetUp.comment("pmale=" + pmale);

			// Check second if it is a female
			double pfemale = overallP(words, SetUp.GENDER.FEMALE);
			SetUp.comment("pfemale=" + pfemale);

			// Conclude shit
			if (pmale > pfemale) {
				SetUp.comment("It's a manly sentence<<<");
				return SetUp.GENDER.MALE;
			} else if (pfemale > pmale){
				SetUp.comment("It's a female sentence");
				return SetUp.GENDER.FEMALE;
			} else {
				SetUp.comment("It's not a manly nor a female sentence");
				return GENDER.UNKNOWN;
			}
		}
	}

	private String[] getWords(String data) {
		return extractToWords(data.substring(wordsPrefix.length(), data.length()));
	}

	private static void comment(String comment) {
		SetUp.comment(comment);
		
	}

	private boolean isWords(String data) {
		return data.startsWith(wordsPrefix);
	}

	private boolean majorityOf(List<GENDER> gen, GENDER male) {
		comment(gen.toString());
		Collections.sort(gen);
		comment(gen.toString());
		int NRmale = Collections.frequency(gen, GENDER.MALE);
		int NRfemale = Collections.frequency(gen, GENDER.FEMALE);
		
		comment("There are "+NRmale+" male sentences and "+NRfemale+" female sentences");
		return (NRmale>NRfemale);
	}

	public static void insert(String word, double number, SetUp.GENDER gender) {
		gender.getList().put(word, number);
	}

	public static void fillDB() {
		// insert("male", 5, SetUp.GENDER.MALE);
		// insert("dude", 2, SetUp.GENDER.MALE);
		// insert("herman", 1, SetUp.GENDER.MALE);
		// insert("henk", 1, SetUp.GENDER.MALE);
		// insert("female", 5, SetUp.GENDER.FEMALE);
		// insert("ladies", 2, SetUp.GENDER.FEMALE);
		// insert("love", 5, SetUp.GENDER.FEMALE);
		// insert("i", 15, SetUp.GENDER.FEMALE);
		// insert("i", 10, SetUp.GENDER.MALE);
		String maleFolder = "resources/blogstrain/M/";
		String femaleFolder = "resources/blogstrain/F/";
		for (String maleBlogs: SetUp.getFilesInFolder(maleFolder)){
			SetUp.processTrainingFile(maleFolder+maleBlogs,GENDER.MALE);
		}
		for (String femaleBlogs: SetUp.getFilesInFolder(femaleFolder)){
			SetUp.processTrainingFile(femaleFolder+femaleBlogs,GENDER.FEMALE);
		}
		
		prepareCounts(SetUp.GENDER.FEMALE);
		prepareCounts(SetUp.GENDER.MALE);
		comment("Databases are filled.");
		System.out.println("Coffee in male is"+GENDER.MALE.getList().get("coffee"));
		System.out.println("Coffee in female is"+GENDER.FEMALE.getList().get("coffee"));
		System.out.println("Relationship in male is"+GENDER.MALE.getList().get("relationship"));
		System.out.println("Relationship in female is"+GENDER.FEMALE.getList().get("relationship"));
		comment(showHashMap(GENDER.MALE.getList()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static Map sortByValue(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	              .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 
	private static String showHashMap(HashMap<String, Double> list) {
		list = (HashMap<String, Double>) sortByValue(list);
		String res = "";
		for (Entry<String, Double> entry: list.entrySet()){
			res +=entry.getKey()+" => "+entry.getValue()+"\n";
		}
		return res;
	}

	public static void prepareCounts(SetUp.GENDER gender) {
		Iterator<Double> it = gender.getList().values().iterator();
		int TotNum = 0;
		while (it.hasNext()) {
			TotNum += it.next();
		}
		gender.listSize = TotNum;
		SetUp.comment("ListSize for " + gender.name() + " = " + gender.listSize);
		gender.vocLength = gender.getList().size();
	}

	public static void main(String[] args) {
		Classifier c = new Classifier();
		fillDB();
		System.out.println("MALE? "
				+ c.checkGender("Yo Dude, my male henk is fucking herman"));
		System.out.println("FEMALE? "
				+ c.checkGender("my love is sweet like i am"));
		System.out.println("MALE? "
				+ c.checkGender("henk has a dude, herman, i love"));
		System.out.println("???? " + c.checkGender("no idea"));
		SetUp.closeLog();
	}
}
