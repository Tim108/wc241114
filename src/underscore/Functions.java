package underscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Martijn Willemsen & Tim Sonderen
 * 
 * Class that contains help functions for the other classes.
 *
 */
public class Functions {
	private static BufferedWriter log;
	public static final double k = 0.01;
	public static final boolean useRandom = true;
	public static boolean showComments = false;
	
	/**
	 * 
	 * @author T
	 * 
	 * Enum for the gender.
	 * Is either male, female or unknown.
	 * Also contains list size, vocabulary size of the gender.
	 *
	 */
	public static enum GENDER {
		MALE, FEMALE, UNKNOWN;
		public static HashMap<String, Double> maleList = new HashMap<String, Double>();
		public static HashMap<String, Double> femaleList = new HashMap<String, Double>();
		public double listSize;
		public double vocLength;
		
		/**
		 * 
		 * @return Returns Female if called on Male and Male if called on Female.
		 */
		public GENDER other() {
			if (this.equals(FEMALE)) {
				return MALE;
			} else {
				return FEMALE;
			}
		}
		
		/**
		 * 
		 * @return List of words for the gender it is called up on.
		 */
		public HashMap<String, Double> getList() {
			if (this.equals(MALE))
				return maleList;
			else
				return femaleList;
		}
		
		/**
		 * 
		 * @param word Word of which the occurrence frequency should be returned.
		 * @return Returns how many times the word occurs in the dictionary.
		 */
		public double getFrequency(String word) {
			if (this.getList().containsKey(word))
				return getList().get(word);
			else
				return 0;
		}
	};
	
	/**
	 * 
	 * @param foldername Target folder.
	 * @return Returns all files in given folder.
	 */
	public String[] getFilesInFolder(String foldername) {
		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();
		String[] listOfFilenames = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				listOfFilenames[i] = listOfFiles[i].getName();
			}
		}
		return listOfFilenames;
	}
	
	/**
	 * 
	 * @param firstArray
	 * @return Returns same array but without null's.
	 */
	public String[] removeNullFromArray(String[] firstArray) {
		List<String> list = new ArrayList<String>();

		for (String s : firstArray) {
			if (s != null && s.length() > 0) {
				list.add(s);
			}
		}

		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * Initializes the variables for the enum GENDER.
	 * @param gender
	 */
	public void prepareCounts(GENDER gender) {
		Iterator<Double> it = gender.getList().values().iterator();
		int TotNum = 0;
		while (it.hasNext()) {
			TotNum += it.next();
		}
		gender.listSize = TotNum;
		comment("NumberOfWords for " + gender.name() + " = " + gender.listSize);
		gender.vocLength = 0;
		for (double i : gender.getList().values()) {
			gender.vocLength += i;
		}
		comment("NumberOfDIFFERENTWords for " + gender.name() + " = "
				+ gender.vocLength);

	}
	
	/**
	 * Read given text file.
	 * @param file
	 * @return Returns the text that is in the file.
	 */
	public String readTextfile(String file) {
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				text += " " + line;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * Initialises a log file.
	 */
	public static void instantiateLog() {
		try {
			log = new BufferedWriter(new FileWriter("logfile.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the log file.
	 */
	public static void closeLog() {
		try {
			if (log != null)
				log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to print in console and also put in the log file.
	 * @param comment
	 */
	public static void comment(String comment) {
		if (showComments) {
			System.out.println(comment);
			try {
				if (log == null) {
					instantiateLog();
				}
				log.newLine();
				log.write(comment);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * shortens the given string if it is too long.
	 * @param sentence
	 * @return
	 */
	public String maxLenght(String sentence) {
		if (sentence.length() > 20) {
			return sentence.substring(0, 10) + " ... "
					+ sentence.substring(sentence.length() - 10);
		} else {
			return sentence;
		}
	}
	
	/**
	 * Extracts an array of words and removes any punctuation from the given string.
	 * @param dataArg
	 * @return Returns a String Array with words without spaces and punctuation.
	 */
	public String[] extractToWords(String dataArg) {
		dataArg = Normalizer.normalize(dataArg, Normalizer.Form.NFD);
		String[] data = dataArg.toLowerCase().split("[^a-zA-Z]");
		List<String> result = new LinkedList<String>();
		for (String s : data) {

			if (s != null && !s.equals(" ") && !s.equals("")) {
				result.add(s);
			}
		}
		int i = 0;
		String[] resData = new String[result.size()];
		for (String x : result) {
			resData[i] = x;
			i++;
		}
		return resData;
	}
	
	/**
	 * Processes the given training file for given gender
	 * @param filename
	 * @param gen
	 */
	public void processTrainingFile(String filename, GENDER gen) {
		String text = readTextfile(filename);
		processTrainingData(text, gen);
	}

	/**
	 * Processes the data from the training file for given gender.
	 * @param text
	 * @param gen
	 */
	public void processTrainingData(String text, GENDER gen) {
		// First we will extract all the data
		String[] words = extractToWords(text);
		// Sort words
		Arrays.sort(words);
		// Put into map
		HashMap<String, Double> map = gen.getList();
		HashMap<String, Double> otherMap = gen.other().getList();
		for (String word : words) {
			if (map.containsKey(word)) {
				Double num = map.get(word);
				map.remove(word);
				map.put(word, num + 1);
			} else {
				map.put(word, (double) 1);
			}
			if (!otherMap.containsKey(word)) {
				otherMap.put(word, (double) 0);
			}
		}
	}
}
