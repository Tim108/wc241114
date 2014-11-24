package underscore;

import java.util.HashMap;
import java.util.LinkedList;

public class SetUp {
	public static enum GENDER {
		MALE, FEMALE;
		public static HashMap<String, Integer> maleList = new HashMap<String, Integer>();
		public static HashMap<String, Integer> femaleList = new HashMap<String, Integer>();
		
		public HashMap<String, Integer> getList() {
			if (this.equals(MALE))
				return maleList;
			else
				return femaleList;
		}
		
		public double getFrequentcy(String word, GENDER gen) {
			System.out.println(getList().size());
			return getList().
					get(word)/
					getList().
					size();
		}
	};

	// public static Map<Integer, LinkedList<String>> = new HashMap<Integer,
	// LinkedList<String>>();
	public static final String punctuation = "(){},.;!?<>%" + "'\"*#_";
}
