package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import data.Headphone;
import parse.InvalidDocumentException;
import parse.MeasurementParser;
import util.HeadphoneList;

/**
 * This program gets headphone measurement PDFs from Innerfidelity.com,
 * parses the documents into individual graphs,
 * reads and stores information about the headphones from the graphs,
 * and provides information based on comparisons between measurements.
 * 
 * @author Adam Luck
 */
public class HeadphoneAnalyzer {

	/** URL of Innerfidelity measurement PDFs. */
	private static String measurementsLocation = "https://www.innerfidelity.com/headphone-measurements";
	/** Array containing the HeadphoneLists for each type of headphone */
	private HeadphoneList[] headphones;
	
	/**
	 * Constructs an array of empty HeadphoneLists.
	 */
	public HeadphoneAnalyzer() {
		String[] types = Headphone.HEADPHONE_TYPES;
		headphones = new HeadphoneList[types.length];
		for (int i = 0; i < types.length; i++) {
			headphones[i] = new HeadphoneList(types[i]);
		}
	}
	
	/**
	 * Gets the measurement PDF and stores all of the headphone measurements.
	 */
	public void getHeadphoneMeasurements() {
	
		loadCurrentHeadphones();
		
		loadNewHeadphones();
		
		// TODO Use Analyzer to analyze user input and give headphone recommendations.
		
		saveNewHeadphones();
	}

	/**
	 * Loads the headphone information for all measurements that have already been parsed.
	 */
	private void loadCurrentHeadphones() {
		// TODO Check Headphones folder for .txt files containing previously processed headphones.
		// 		Add all headphones to the list.
		
	}
	
	/**
	 * Checks Innerfidelity for any new measurement sheets that have been uploaded.
	 */
	private void loadNewHeadphones() {
		
		try {
			URL measurementsURL = new URL(measurementsLocation);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// TODO Use PDFParser to set information for new headphones
		String path = "C:\\Users\\Adam\\Downloads\\XiaomiPiston2.pdf";
		Headphone headphone = new Headphone("Xiaomi Piston 2", "In-Ear");
		try {
			MeasurementParser.parseMeasurements(path, headphone);
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Saves all headphone measurements that don't already exist.
	 */
	private void saveNewHeadphones() {
		for (HeadphoneList list : headphones) {
			String type = list.getType();
			for (Headphone headphone : list.getAll()) {
				String name = headphone.getName();
				String path = "./Headphones/" + type + "/" + name + "/" + name + ".txt";
				File measurementFile = new File(path);
				if (!measurementFile.exists()) {
					saveHeadphone(headphone, path);
				}
			}
		}
	}

	/**
	 * Saves the measurement information of the given headphone in a text file.
	 * @param headphone Headphone with measurements to save.
	 * @param path Path of the file to save the information in.
	 */
	private void saveHeadphone(Headphone headphone, String path) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(headphone.getName() + "\n");
			double[] dBVals = headphone.getDBVals();
			for (int i = 0; i < dBVals.length; i++) {
				bw.write(Double.toString(dBVals[i]) + " ");
			}
			bw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}

}
