package underscore;

import java.util.HashMap;

public class SetUp {
	public static enum GENDER {
		MALE, FEMALE;
		public static HashMap<String, Double> maleList = new HashMap<String, Double>();
		public static HashMap<String, Double> femaleList = new HashMap<String, Double>();
		public int listSize;
		
		public HashMap<String, Double> getList() {
			if (this.equals(MALE))
				return maleList;
			else
				return femaleList;
		}
		
		public double getFrequentcy(String word, GENDER gen) {
			return (getList().get(word)/getList().size());
		}
	};

	// public static Map<Integer, LinkedList<String>> = new HashMap<Integer,
	// LinkedList<String>>();
	public static final String punctuation = "(){},.;!?<>%" + "'\"*#_";
}
