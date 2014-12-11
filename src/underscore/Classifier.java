package underscore;

public class Classifier extends Functions {

	private static int i = 0;

	public Classifier() {

	}

	/**
	 * 2.2.1
	 * 
	 * @param w
	 * @param given
	 * @return
	 */
	public double p(String w, GENDER given) {
		double C = given.getFrequency(w);
		double V = 2;// given.vocLength+given.other().vocLength;
		double N = given.listSize + given.other().listSize;
		double res = (C + k) / (N + k * V);
		if (Double.isNaN(res)){
		comment("P(" + w + "|" + given.name() + ")=(" + C + "+" + k
		 + ") / (" + N + "+" + k + "*" + V + ")=" + res);
		}
		return Math.log(res) / Math.log(2);
	}

	public double overallP(String sentence, GENDER given) {
		String[] words = extractToWords(sentence);
		double PwordsGiven = 1;
		double PwordsOther = 1;
		for (String word : words) {
			PwordsGiven *= p(word, given); //Dit wordt nu -Infinity, daarom krijgen we een NaN
			PwordsOther *= p(word, given.other());//Dit wordt nu -Infinity, daarom krijgen we een NaN
		}
		double res = Math.log(PwordsGiven
				* given.prior
				/ (PwordsGiven * given.prior + PwordsOther
						* given.other().prior))
				/ Math.log(2);
		if (Double.isNaN(res)){
			comment("P(" + given.name() + "|" + maxLenght(sentence)+ ")="+PwordsGiven+
				"*"+ given.prior+
				"/ ("+PwordsGiven +"*"+ given.prior +"+"+ PwordsOther+
						"*"+ given.other().prior+"=" + res);
					}
		return res;
	}

	public GENDER checkGender(String sentence) {
		double PMale = overallP(sentence, GENDER.MALE);
		double PFemale = overallP(sentence, GENDER.FEMALE);
		if (PMale > PFemale) {
			return GENDER.MALE;
		} else if (PMale < PFemale) {
			return GENDER.FEMALE;
		} else {
			i++;
			comment("ITS UNKNOWN: " + PMale + "==" + PFemale + ". This is the "
					+ (i) + "th time. The tested sentence is: " + sentence);
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
