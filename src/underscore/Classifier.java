package underscore;

/**
 * 
 * @author Martijn Willemsen & Tim Sonderen
 * 
 * Class that contains most of the main classifier functions.
 */
public class Classifier extends Functions {
	/**
	 * Calculates the chance that String w occurs in the text, given the GENDER
	 * given.
	 * 
	 * @param w
	 *            Word of which occurrence should be calculated.
	 * @param given
	 *            Gender in which String w should occur.
	 * @return Returns the chance that String w occurs in the text, given Gender
	 *         given.
	 */
	public double p(String w, GENDER given) {
		double C = given.getFrequency(w);
		double V = given.listSize; // amount of different words in dictionary
		double N = given.vocLength; // amount of words in dictionary
		double res = Math.log((float) (C + k))
				- Math.log((float) (N + (k * V)));
		return res / Math.log(2);
	}

	/**
	 * Calculates the chance that String sentence occurs, given GENDER given.
	 * 
	 * @param sentence
	 *            Sentence of which occurrence should be calculated.
	 * @param given
	 *            Gender of which String sentence should occur.
	 * @return Returns the chance that String sentence occurs in the text, given
	 *         Gender given.
	 */
	public double overallP(String sentence, GENDER given) {
		String[] words = extractToWords(sentence);
		float res = 1;
		for (String word : words) {
			if (word != null) {
				res += p(word, given);
			}
		}
		return res;
	}

	/**
	 * Calculates what gender the String sentence probably is.
	 * 
	 * @param sentence
	 *            Sentence of which the most probable gender should be
	 *            calculated.
	 * @return The probable gender which the sentence is.
	 */
	public GENDER checkGender(String sentence) {
		double PMale = overallP(sentence, GENDER.MALE);
		double PFemale = overallP(sentence, GENDER.FEMALE);
		if (PMale > PFemale) {
			comment("P(Male)= " + PMale);
			comment("P(Female)= " + PFemale);
			System.out.println(sentence);
			return GENDER.MALE;
		} else if (PMale < PFemale) {
			comment("P(Male)= " + PMale);
			comment("P(Female)= " + PFemale);
			return GENDER.FEMALE;
		} else {
			if (useRandom) {
				if (Math.random() > 0.5) {
					return GENDER.MALE;
				} else {
					return GENDER.FEMALE;
				}
			} else {
				return GENDER.UNKNOWN;
			}
		}
	}
}
