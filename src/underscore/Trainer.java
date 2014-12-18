package underscore;

public class Trainer extends Functions {

	/**
	 * Train the data in a classifier
	 * 
	 * @param dirMale
	 *            directory for all male files
	 * @param dirFemale
	 *            directory for all female files
	 */
	public Trainer(String dirMale, String dirFemale) {
		// Get all male documents
		String[] maleFiles = getFilesInFolder(dirMale);
		String[] femaleFiles = getFilesInFolder(dirFemale);

		// Put all words in the dictionary
		for (String file : maleFiles) {
			processTrainingFile(dirMale + "/" + file, GENDER.MALE);
		}
		for (String file : femaleFiles) {
			processTrainingFile(dirFemale + "/" + file, GENDER.FEMALE);
		}

		prepareCounts(GENDER.MALE);
		prepareCounts(GENDER.FEMALE);
	}
}
