package underscore;

import java.util.HashMap;
/**
 * 
 * @author Martijn Willemsen & Tim Sonderen
 * 
 * Class that contains the main program of the classifier.
 *
 */
public class MaleFemaleTester extends Functions {

	private Classifier c;
	private int numberOfTests = 0;
	private int numberOfCorrectResults = 0;
	// The first GENDER is how it should be classified, and the second how many
	// are classified like that
	private static HashMap<GENDER, Integer> testCorrectResults = new HashMap<GENDER, Integer>();
	private static HashMap<GENDER, Integer> testWrongResults = new HashMap<GENDER, Integer>();
	
	/**
	 * Executes the tests with a confusion matrix at the end.
	 * @param filename
	 * @param gender
	 * @return
	 */
	public boolean test(String filename, GENDER gender) {
		c = new Classifier();
		numberOfTests++;
		GENDER gen = c.checkGender(readTextfile(filename));
		comment("The classifier predicted this gender: " + gen
				+ ". This is the correct gender: " + gender);
		if (gender.equals(gen)) {
			numberOfCorrectResults++;
			testCorrectResults.put(gender,
					notNULL(testCorrectResults.get(gender)) + 1);
			return true;
		} else {
			testWrongResults.put(gender,
					notNULL(testWrongResults.get(gender)) + 1);
			return false;
		}
	}
	
	/**
	 * Makes sure given integer is not null.
	 * @param integer
	 * @return Returns given integer or 0.
	 */
	private static int notNULL(Integer integer) {
		if (integer == null)
			return 0;
		else
			return integer;
	}
	
	/**
	 * Launch application for testing without a GUI.
	 * Contains selection of which training and testing files to use.
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String dirBlogstrainM = "resources/blogstrain/M";
		String dirBlogstrainF = "resources/blogstrain/F";
		String dirSpamtrainHam = "resources/spamtrain/ham";
		String dirSpamtrainSpam = "resources/spamtrain/spam";

		String dirBlogstestM = "resources/blogstest/M";
		String dirBlogstestF = "resources/blogstest/F";
		String dirSpamtestHam = "resources/spamtest/ham";
		String dirSpamtestSpam = "resources/spamtest/spam";

		new Trainer(dirBlogstrainM, dirBlogstrainF);
		MaleFemaleTester test = new MaleFemaleTester();

		String folder = dirBlogstestM;
		String[] files = test
				.removeNullFromArray(test.getFilesInFolder(folder));
		for (String file : files) {
			test.test(folder + "//" + file, GENDER.MALE);
		}
		folder = dirBlogstestF;
		files = test.removeNullFromArray(test.getFilesInFolder(folder));
		for (String file : files) {
			test.test(folder + "//" + file, GENDER.FEMALE);
		}
		if (test.numberOfTests > 0) {
			double prc = ((double) ((double) test.numberOfCorrectResults / (double) test.numberOfTests)) * 100;
			showComments = true;
			comment("There were " + test.numberOfCorrectResults
					+ " tests that were succesfull (out of "
					+ test.numberOfTests + "). This is " + prc + "%.");
			showComments = false;
		}
		confusionMatrix();
		closeLog();
	}

	/**
	 * Creates confusion matrix.
	 */
	private static void confusionMatrix() {
		// Get MALE correct
		Integer mc = notNULL(testCorrectResults.get(GENDER.MALE));
		// Get MALE incorrect
		Integer mi = notNULL(testWrongResults.get(GENDER.MALE));
		// Get FEMALE correct
		Integer fc = notNULL(testCorrectResults.get(GENDER.FEMALE));
		// Get FEMALE incorrect
		Integer fi = notNULL(testWrongResults.get(GENDER.FEMALE));
		System.out.println("  |\tM\t|\tF\t|" + "\n" + "M |\t" + mc + "\t|\t"
				+ mi + "\n" + "F |\t" + fi + "\t|\t" + fc);

	}

}
