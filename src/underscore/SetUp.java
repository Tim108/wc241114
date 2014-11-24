package underscore;

import java.util.HashMap;
import java.util.LinkedList;

public class SetUp {
	public static enum GENDER {
		MALE, FEMALE;
		public static HashMap<String, Integer> maleList = new HashMap<String, Integer>();
		public static HashMap<String, Integer> femaleList = new HashMap<String, Integer>();
		
		public HashMap<String, Integer> getList(GENDER gen) {
			if (gen.equals(MALE))
				return maleList;
			else
				return femaleList;
		}
		
		public double getFrequentcy(String word, GENDER gen) {
			return getList(gen).get(word)/getList(gen).size();
		}
	};

	// public static Map<Integer, LinkedList<String>> = new HashMap<Integer,
	// LinkedList<String>>();
	public static final String punctuation = "(){},.;!?<>%" + "'\"*#_";
}
