package underscore;

public class MaleFemaleTester {

	private Classifier c;
	private int numberOfTests = 0;
	private int numberOfCorrectResults = 0;
	private int numberOfFalseResults = 0;
	
	public MaleFemaleTester() {
	}
	
	public boolean test (String filename, SetUp.GENDER gender){
		c = new Classifier();
		numberOfTests++;
		if (gender.equals(c.checkGender(SetUp.readTextfile(filename)))) {
			numberOfCorrectResults++;
			return true;
		}
		else {
			numberOfFalseResults++;
			return false;
		}
	}

	public static void main(String[] args) {
		MaleFemaleTester test = new MaleFemaleTester();
		Classifier.showComments = true;
		Classifier.fillDB();
		String folder = "resources/blogstrain/M";
		String file=SetUp.getFilesInFolder(folder)[15];
		//for(){
			test.test(folder+"//"+file, SetUp.GENDER.MALE);
		//}
		if (test.numberOfTests > 0)
		System.out.println("There were "+test.numberOfCorrectResults+" tests that were succesfull (out of "+test.numberOfTests+"). This is "+((test.numberOfCorrectResults/test.numberOfTests)*100)+"%.");
	}

}
