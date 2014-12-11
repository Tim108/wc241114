package underscore;

import java.util.Arrays;
import java.util.HashMap;

public class Trainer extends Functions {

	
	/**
	 * Train the data in a classifier
	 * @param dirMale directory for all male files
	 * @param dirFemale directory for all female files
	 */
	public Trainer(String dirMale, String dirFemale) {
		//Get all male documents
		String[] maleFiles = getFilesInFolder(dirMale);
		String[] femaleFiles = getFilesInFolder(dirFemale);
		
		//Put all words in the dictionary
		for (String file: maleFiles){
			processTrainingFile(dirMale+"/"+file, GENDER.MALE);
		}
		for (String file: femaleFiles){
			processTrainingFile(dirFemale+"/"+file, GENDER.FEMALE);
		}
		//Now show me the dictionary
		comment("The male dictionary: "+showHashMap(GENDER.MALE.getList()));
		comment("The female dictionary: "+showHashMap(GENDER.FEMALE.getList()));
		
		//Now calculate the prior probabilities
		calculatePrior(GENDER.MALE);
		calculatePrior(GENDER.FEMALE);
	}
}
