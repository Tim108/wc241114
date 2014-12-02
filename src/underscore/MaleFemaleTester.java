package underscore;

import java.util.HashMap;

import underscore.SetUp.GENDER;

public class MaleFemaleTester {

	private Classifier c;
	private int numberOfTests = 0;
	private int numberOfCorrectResults = 0;
	private int numberOfFalseResults = 0;
	//The first GENDER is how it should be classified, and the second how many are classified like that
	private static HashMap<GENDER, Integer> testCorrectResults = new HashMap<GENDER, Integer>();
	private static HashMap<GENDER, Integer> testWrongResults = new HashMap<GENDER, Integer>();
	public MaleFemaleTester() {
	}
	
	public boolean test (String filename, SetUp.GENDER gender){
		c = new Classifier();
		numberOfTests++;
		GENDER gen = c.checkGender(SetUp.readTextfile(filename));
		comment("+++++ THE GENDER: "+gen+" equals to: "+gender);
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
		MaleFemaleTester test = new MaleFemaleTester();
		Classifier.showComments = true;
		Classifier.fillDB();
		String folder = "resources/blogstest/M";
		String[] files=SetUp.getFilesInFolder(folder);
		for(String file: files){
			test.test(folder+"//"+file, SetUp.GENDER.MALE);
		}
		folder = "resources/blogstest/F";
		files=SetUp.getFilesInFolder(folder);
		for(String file: files){
			test.test(folder+"//"+file, SetUp.GENDER.FEMALE);
		}
		if (test.numberOfTests > 0)
		comment("There were "+test.numberOfCorrectResults+" tests that were succesfull (out of "+test.numberOfTests+"). This is "+((test.numberOfCorrectResults/test.numberOfTests)*100)+"%.");
		
		//testCorrectResults.put(GENDER.MALE, 5);
		//testCorrectResults.put(GENDER.FEMALE, 15);
		//testWrongResults.put(GENDER.MALE, 25);
		//testWrongResults.put(GENDER.FEMALE, 35);
		confusionMatrix();
		SetUp.closeLog();
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

	private static void comment(String string) {
		// TODO Auto-generated method stub
		SetUp.comment(string);
	}

}
