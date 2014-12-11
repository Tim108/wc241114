package underscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Functions {
	public final String punctuation = "(){},;!?<>%" + "'\"*#_.";
	private static BufferedWriter log;
	public static final double k = 1;
	public static final boolean useRandom = false;
	public static final boolean showComments = true;
	
	
	public static enum GENDER {
		MALE, FEMALE, UNKNOWN;
		public static HashMap<String, Double> maleList = new HashMap<String, Double>();
		public static HashMap<String, Double> femaleList = new HashMap<String, Double>();
		public double listSize;
		public double vocLength;
		public double prior;

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
	
	public String[] getFilesInFolder(String foldername) {
		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();
		String[] listOfFilenames = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				//comment("File " + listOfFiles[i].getName()+" is read ("+listOfFiles[i].getPath()+")");
				listOfFilenames[i]=listOfFiles[i].getName();
			} else if (listOfFiles[i].isDirectory()) {
				//comment("Directory " + listOfFiles[i].getName()+"is not read, please remove any directories.");
			}
		}
		return listOfFilenames;
	}
	
	public String[] removeNullFromArray(String[] firstArray){
		List<String> list = new ArrayList<String>();

	    for(String s : firstArray) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }

	    return list.toArray(new String[list.size()]);
	}

	public double calculatePrior(GENDER given) {
		prepareCounts(given);
		double C = given.listSize;
		double k = 1;
		double V = given.vocLength+given.other().vocLength;
		double N = given.listSize+given.other().listSize;
		double res = (C + k) / (N + k * V);
		comment("P("+given.name() + ")=(" + C + "+" + k
				+ ") / (" + N + "+" + k + "*" + V + ")=" + res);
		given.prior=res;
		return res;
	}
	
	public void prepareCounts(GENDER gender) {
		Iterator<Double> it = gender.getList().values().iterator();
		int TotNum = 0;
		while (it.hasNext()) {
			TotNum += it.next();
		}
		gender.listSize = TotNum;
		comment("NumberOfWords for " + gender.name() + " = " + gender.listSize);
		gender.vocLength = gender.getList().size();
		comment("NumberOfDIFFERENTWords for " + gender.name() + " = " + gender.vocLength);
		
	}

	public String readTextfile(String file) {
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				text += " "+line;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	public static void instantiateLog (){
		try {
			log = new BufferedWriter(new FileWriter("logfile_"+new Date().toString().replace(" ", "_").replace(":", "-")+".txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closeLog(){
		try {
			if (log != null)
			log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void comment(String comment) {
		if (showComments) {
			System.out.println(comment);
			try {
				if (log == null){
					instantiateLog();
				}
				log.newLine();
				log.write(comment);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String maxLenght(String sentence) {
		if (sentence.length() > 20){
			return sentence.substring(0, 10)+" ... "+sentence.substring(sentence.length()-10);
		}
		else {
			return sentence;
		}
	}
	
	public String showHashMap(HashMap<String, Double> list) {
		//list = (HashMap<String, Double>) sortByValue(list);
		String res = "";
		for (Entry<String, Double> entry: list.entrySet()){
			res +=entry.getKey()+" => "+entry.getValue()+"\n";
		}
		return res;
	}
	
	//DEZE FUNCTIE IS NOG OUD
	public String[] extractToWords(String data) {
		data = Normalizer.normalize(data, Normalizer.Form.NFD);
		String[] returnData = data.replaceAll("[" + punctuation + ".]", "").toLowerCase()
				.trim().replace("[", "").replace("]", "").replaceAll("\\s+", " ").split(" ");
		//SetUp.comment(Arrays.toString(returnData));
		return returnData;
	}
}
