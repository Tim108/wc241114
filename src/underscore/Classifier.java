package underscore;

public class Classifier extends Functions {
	/**
	 * 2.2.1
	 * 
	 * @param w
	 * @param given
	 * @return
	 */
	public double p(String w, GENDER given) {
		double C = given.getFrequency(w);
		double V = given.listSize;// given.vocLength+given.other().vocLength;
		double N = given.vocLength;
		double res = Math.log((float) (C + k))
				- Math.log((float) (N + (k * V)));
		return res / Math.log(2);
		// return res;
	}

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

	public GENDER checkGender(String sentence) {
		double PMale = overallP(sentence, GENDER.MALE);
		double PFemale = overallP(sentence, GENDER.FEMALE);
		if (PMale > PFemale) {
			comment("P(Male)= " + PMale);
			comment("P(Female)= " + PFemale);
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
