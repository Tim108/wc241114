package underscore;

import java.io.BufferedReader;
import java.io.FileReader;
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
			return (getList().get(word) / getList().size());
		}
	};

	// public static Map<Integer, LinkedList<String>> = new HashMap<Integer,
	// LinkedList<String>>();
	public static final String punctuation = "(){},.;!?<>%" + "'\"*#_";

	public static String readTextfile(String file) {
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				text += line + " ";
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
}
