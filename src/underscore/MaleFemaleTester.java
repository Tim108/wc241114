package underscore;

import java.util.Arrays;
import java.util.HashMap;

import underscore.Functions.GENDER;

public class MaleFemaleTester extends Functions {

	private Classifier c;
	private int numberOfTests = 0;
	private int numberOfCorrectResults = 0;
	private int numberOfFalseResults = 0;
	//The first GENDER is how it should be classified, and the second how many are classified like that
	private static HashMap<GENDER, Integer> testCorrectResults = new HashMap<GENDER, Integer>();
	private static HashMap<GENDER, Integer> testWrongResults = new HashMap<GENDER, Integer>();
	public MaleFemaleTester() {
	}
	
	public boolean test (String filename, GENDER gender){
		c = new Classifier();
		numberOfTests++;
		GENDER gen = c.checkGender(readTextfile(filename));
		comment("The classifier predicted this gender: "+gen+". This is the correct gender: "+gender);
		if (gender.equals(gen)) {
			numberOfCorrectResults++;
			testCorrectResults.put(gender, notNULL(testCorrectResults.get(gender))+1);
			return true;
		}
		else {
			numberOfFalseResults++;
			testWrongResults.put(gender, notNULL(testWrongResults.get(gender))+1);
			return false;
		}
	}

	private static int notNULL(Integer integer) {
		if (integer==null)
			return 0;
		else 
			return integer;
	}

	public static void main(String[] args) {
		String dirBlogstrainM = "resources/blogstrain/M";
		String dirBlogstrainF = "resources/blogstrain/F";
		String dirSpamtrainHam = "resources/spamtrain/ham";
		String dirSpamtrainSpam = "resources/spamtrain/spam";
		String dirTesttrainM = "resources/testje/HAM";
		String dirTesttrainF = "resources/testje/SPAM";

		String dirBlogstestM = "resources/blogstest/M";
		String dirBlogstestF = "resources/blogstest/F";
		String dirSpamtestHam = "resources/spamtest/ham";
		String dirSpamtestSpam = "resources/spamtest/spam";
		String dirTesttestM = "resources/testje";
		String dirTesttestF = "resources/testje";
		
		new Trainer(dirBlogstrainM, dirBlogstrainF);
		MaleFemaleTester test = new MaleFemaleTester();
		
		String folder = dirBlogstestM;
		String[] files=test.removeNullFromArray(test.getFilesInFolder(folder));
		for(String file: files){
			test.test(folder+"//"+file, GENDER.MALE);
		}
		folder = dirBlogstestF;
		files=test.removeNullFromArray(test.getFilesInFolder(folder));
		for(String file: files){
			test.test(folder+"//"+file, GENDER.FEMALE);
		}
		if (test.numberOfTests > 0){
			double prc = ((double)((double)test.numberOfCorrectResults/(double)test.numberOfTests))*100;
			comment("There were "+test.numberOfCorrectResults+" tests that were succesfull (out of "+test.numberOfTests+"). This is "+ prc +"%.");
		}
		//testCorrectResults.put(GENDER.MALE, 5);
		//testCorrectResults.put(GENDER.FEMALE, 15);
		//testWrongResults.put(GENDER.MALE, 25);
		//testWrongResults.put(GENDER.FEMALE, 35);
		confusionMatrix();
		closeLog();
	}

	private static void confusionMatrix() {
		//Get MALE correct
		Integer mc = notNULL(testCorrectResults.get(GENDER.MALE));
		int mcl = mc.toString().length();
		//Get MALE incorrect
		Integer mi = notNULL(testWrongResults.get(GENDER.MALE));
		int mil = mi.toString().length();
		//Get FEMALE correct
		Integer fc = notNULL(testCorrectResults.get(GENDER.FEMALE));
		int fcl = fc.toString().length();
		//Get FEMALE incorrect
		Integer fi = notNULL(testWrongResults.get(GENDER.FEMALE));
		int fil = fi.toString().length();
		//Print shit
		int l = Math.max(Math.max(fcl, fil),Math.max(mcl, mil));
		
			System.out.println(  "  |\tM\t|\tF\t|" +"\n"
					+ "M |\t"+mc+"\t|\t"+mi+"\n"
					+ "F |\t"+fi+"\t|\t"+fc);
		
	}

}
