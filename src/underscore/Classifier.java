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
		double V = given.listSize;// given.vocLength+given.other().vocLength;
		double N = given.vocLength;
		double res = Math.log((float)(C+k)) - Math.log((float)(N + (k*V)));
		System.out.println("c = " + C + " | v = " + V + " | n = " + N + " | res = " + res);
		return res / Math.log(2);
		//return res;
	}

	public double overallP(String sentence, GENDER given) {
		String[] words = extractToWords(sentence);
		float PwordsGiven = 1;
		for (String word : words) {
			if (word != null) {
				PwordsGiven += p(word, given);
				//PwordsOther *= Math.log(p(word, given.other()))/Math.log(1000);
				//System.out.println("##########" + PwordsGiven + "-----" + PwordsOther);
			}
			
		}
		System.out.println("~~~~~~~~~~" + given.name() + PwordsGiven);
		float res = PwordsGiven;
				//* (Math.log(given.prior) / Math.log(500));
				/*/ (PwordsGiven * given.prior + PwordsOther
						* given.other().prior);*/
		if (Double.isNaN(res)) {
			/*comment("P(" + given.name() + "|" + maxLenght(sentence) + ")="
					+ PwordsGiven + "*" + given.prior + "/ (" + PwordsGiven
					+ "*" + given.prior + "+" + PwordsOther + "*"
					+ given.other().prior + "=" + res)*/;
		}
		System.out.println("res = " + res);
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
			i++;
			/*comment("ITS UNKNOWN: " + PMale + "==" + PFemale + ". This is the "
					+ (i) + "th time. The tested sentence is: " + sentence);*/
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
