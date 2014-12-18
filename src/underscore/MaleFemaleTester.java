package underscore;

import java.util.HashMap;

public class MaleFemaleTester extends Functions {

	private Classifier c;
	private int numberOfTests = 0;
	private int numberOfCorrectResults = 0;
	// The first GENDER is how it should be classified, and the second how many
	// are classified like that
	private static HashMap<GENDER, Integer> testCorrectResults = new HashMap<GENDER, Integer>();
	private static HashMap<GENDER, Integer> testWrongResults = new HashMap<GENDER, Integer>();

	public MaleFemaleTester() {
	}

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

	private static int notNULL(Integer integer) {
		if (integer == null)
			return 0;
		else
			return integer;
	}

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

		new Trainer(dirSpamtrainHam, dirSpamtrainSpam);
		MaleFemaleTester test = new MaleFemaleTester();

		String folder = dirSpamtestHam;
		String[] files = test
				.removeNullFromArray(test.getFilesInFolder(folder));
		for (String file : files) {
			test.test(folder + "//" + file, GENDER.MALE);
		}
		folder = dirSpamtestSpam;
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
