package main;

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
	/**	List of earbuds. */
	private HeadphoneList earbudList;
	/**	List of earpad open headphones. */
	private HeadphoneList earpadOpenList;
	/**	List of earpad sealed headphones. */
	private HeadphoneList earpadSealedList;
	/**	List of full-size open headphones. */
	private HeadphoneList fullSizeOpenList;
	/**	List of full-size closed headphones. */
	private HeadphoneList fullSizeSealedList;
	/**	List of in-ear headphones. */
	private HeadphoneList inEarList;
	/**	List of noise-cancelling headphones. */
	private HeadphoneList noiceCancellingList;
	/**	List of wireless headphones. */
	private HeadphoneList wirelessList;
	
	/**
	 * Gets the measurement PDF and stores all of the headphone measurements.
	 */
	public void getHeadphoneMeasurements() {
	
		loadCurrentHeadphones();
		
		loadNewHeadphones();
		
		// TODO Use ImageParser to set values for all measured frequencies for each headphone.
		
		// TODO Use Analyzer to analyze user input and give headphone recommendations.
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

}
