package underscore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class SetUp {
	public static enum GENDER {
		MALE, FEMALE, UNKNOWN;
		public static HashMap<String, Double> maleList = new HashMap<String, Double>();
		public static HashMap<String, Double> femaleList = new HashMap<String, Double>();
		public double listSize;
		public double vocLength;

		public GENDER other(){
			if (this.equals(FEMALE)){
				return MALE;
			}
			else{
				return FEMALE;
			}
		}
		public HashMap<String, Double> getList() {
			if (this.equals(MALE))
				return maleList;
			else
				return femaleList;
		}

		public double getFrequency(String word) {
			if (this.getList().containsKey(word))
				return getList().get(word);
			else
				return 0;
		}
	};

	// public static Map<Integer, LinkedList<String>> = new HashMap<Integer,
	// LinkedList<String>>();
	public static final String punctuation = "(){},;!?<>%" + "'\"*#_";

	public static String readTextfile(String file) {
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				text += line;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	public static String[] getFilesInFolder(String foldername) {
		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();
		String[] listOfFilenames = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				comment("File " + listOfFiles[i].getName()+" is read ("+listOfFiles[i].getPath()+")");
				listOfFilenames[i]=listOfFiles[i].getName();
			} else if (listOfFiles[i].isDirectory()) {
				comment("Directory " + listOfFiles[i].getName()+"is not read, please remove any directories.");
			}
		}
		return listOfFilenames;
	}
	
	public static void processTrainingFile(String filename, GENDER gen){
		String text = readTextfile(filename);
		processTrainingData(text,gen);
	}
	
	public static void processTrainingData(String text, GENDER gen) {
		// First we will extract all the data
		String[] words = Classifier.extractToWords(text);
		// Sort words
		Arrays.sort(words);
		// Put into map
		HashMap<String, Double> map = gen.getList();
		for (String word : words) {
			if (map.containsKey(word)) {
				Double num = map.get(word);
				map.remove(word);
				map.put(word, num + 1);
			} else {
				map.put(word, (double) 1);
			}
		}
	}

	public static void comment(String comment) {
		if (Classifier.showComments) {
			System.out.println(comment);
		}
	}
}
