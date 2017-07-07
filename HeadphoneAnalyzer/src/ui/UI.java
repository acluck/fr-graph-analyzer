package ui;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import main.HeadphoneAnalyzer;

/**
 * UI for the Headphone Analyzer program.
 * 
 * @author Adam Luck
 */
public class UI {

	/**
	 * Runs the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) throws InvalidPasswordException, IOException {

		HeadphoneAnalyzer analyzer = new HeadphoneAnalyzer();
		analyzer.getHeadphoneMeasurements();

	}
	
}
