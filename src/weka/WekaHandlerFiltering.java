package weka;

import java.io.*;
import java.util.Scanner;

import weka.classifiers.*;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WekaHandlerFiltering {

	public static void main(String[] args){
		Instances data=buildArff(new File(trainpath));
		writeARFF(data, "GeneratedARFF.arff");
		weka.classifiers.Classifier cs = PREFILTERING ? trainJ48(data) : null;//trainFilteredClassifier(data);
		
		double expectedSpam = classifyString(data,cs,"This is spam");
		System.out.println("ExpectedSpam: This is spam is "+( (expectedSpam==1.0) ? "HAM" : "SPAM"));
		
		double expectedHam = classifyString(data,cs,"This is ham");
		System.out.println("ExpectedSpam: This is ham is "+( (expectedSpam==1.0) ? "HAM" : "SPAM"));
			
	}
	
	private static double classifyString(Instances data, Classifier cs,
			String instanceString) {
	Instance newInstance;
	if(PREFILTERING){
	//	newInstance = buildNewFilteredInstanceForData(data, instanceString);
	}else {
	//	newInstance = buildNewUnFilteredInstanceForData(data, instanceString);
	}
	return 0;
	}

	private static J48 trainJ48(Instances data) {
		System.out.println("Training J48");
			try{
				J48 classifierJ48 = new J48();
				classifierJ48.buildClassifier(data);
				System.out.println(classifierJ48);
				return classifierJ48;
			}catch (Exception e){return null;}
	}

	private static final boolean PREFILTERING = false;
	public static String trainpath = "mails";

	private static void writeARFF(Instances data, String fileName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(fileName, "UTF-8");
			pw.print(data);
			pw.close();
		} catch (FileNotFoundException e) {
			//
		} catch (UnsupportedEncodingException e) {

		}
	}

	private static Instance buildNewUnfilteredInstanceForData(Instances data,
			String instanceString) {
		System.out.println("build new unfiltered instance out of: "
				+ instanceString);
		return null;
	}

	public static Instances buildArff(final File folder) {
		FastVector att = new FastVector();
		FastVector classVal = new FastVector();
		FastVector nullValue = null;
		classVal.addElement("SPAM");
		classVal.addElement("HAM");
		att.addElement(new Attribute("content", nullValue));
		att.addElement(new Attribute("@@class", classVal));
		Instances dataRaw = new Instances("Generated ARFF", att, 0);

		cascadeBuildARFF(dataRaw, folder);
		dataRaw.setClassIndex(dataRaw.numAttributes() - 1);

		if (PREFILTERING) {
			System.out.println("Filtering");
			Instances dataFiltered = applyTokenization(dataRaw);
			return dataFiltered;
		} else {
			System.out.println("No filtering");
			return dataRaw;
		}
	}

	private static void cascadeBuildARFF(Instances dataRaw, File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory())
				cascadeBuildARFF(dataRaw, fileEntry);
			else {
				if (fileEntry.getName().contains("spmsgc")) {
					addFileAsContent(fileEntry, dataRaw, true);
				} else if (fileEntry.getName().contains("msg")) {
					addFileAsContent(fileEntry, dataRaw, false);
				} else
					System.out.println("Unknow file: " + fileEntry.getName());
			}
		}
	}

	private static void addFileAsContent(File content, Instances data,
			boolean spam) {
		double[] instanceValue = new double[data.numAttributes()];
		try{
			instanceValue[0] = data.attribute(0).addStringValue(new Scanner(content).useDelimiter("//A").next());
			instanceValue[1] = spam ? 0:1;
			data.add(new Instance(1.0, instanceValue));
		}catch(FileNotFoundException e){}
	}

	private static Instances applyTokenization(Instances dataRaw) {
		StringToWordVector filter = new StringToWordVector();
		try {
			filter.setInputFormat(dataRaw);
			Instances dataFiltered = Filter.useFilter(dataRaw, filter);
			dataFiltered.setClassIndex(0);
			return dataFiltered;
		} catch (Exception e) {
			return null;
		}
		
		
		
	}

}