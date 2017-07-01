package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import data.Headphone;
import parse.ImageParser;
import parse.PDFParser;

/**
 * This program gets headphone measurement PDFs from Innerfidelity.com,
 * parses the documents into individual graphs,
 * reads and stores information about the headphones from the graphs,
 * and provides information based on comparisons between measurements.
 * 
 * @author Adam Luck
 */
public class HeadphoneAnalyzer {

	/**	List of all measured headphones */
	private ArrayList<Headphone> headphones;
	/** PDF parser */
	private PDFParser fileParser;
	
	/**
	 * Gets the measurement PDF and stores all of the headphone measurements.
	 * @throws InvalidPasswordException if the program doesn't have permissions to load the file. 
	 * @throws IOException if the program has issues loading the file.
	 */
	public void getHeadphoneMeasurements() throws InvalidPasswordException, IOException {
	
		// TODO Automate grabbing/loading the AllGraphs.pdf file.
		
		headphones = new ArrayList<Headphone>();
		// TODO Use PDFParser to initialize Headphone objects,
		// 		setting the headphone name, type, and image.
		File file = new File("C:\\Users\\Adam\\Downloads\\XiaomiPiston2.pdf");
		fileParser.parsePDF(file, headphones);
		
		// TODO Use ImageParser to set values for all measured frequencies for each headphone.
		
		// TODO Use Analyzer to analyze user input and give headphone recommendations.
	}
}
