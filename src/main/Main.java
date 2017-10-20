package main;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import io.HeadphoneIO;

/**
 * UI for the Headphone Analyzer program.
 * 
 * @author Adam Luck
 */
public class Main {

	/**
	 * Runs the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		
		System.out.println("Loading headphone measurements...");
		HeadphoneIO.loadHeadphoneMeasurements();
		System.out.println("Saving newly downloaded measurements...");
		HeadphoneIO.saveHeadphoneMeasurements();
		System.out.println("Done!");

		/** Old UI.
			// Get the base headphone.
			System.out.println("Choose the headphone you will be basing your recommendation off of.");
			// Get the type of headphone.
			System.out.println("Types of headphones:");
			for (int i = 0; i < Headphone.HEADPHONE_TYPES.size(); i++) {
				System.out.println("" + i + ": " + Headphone.HEADPHONE_TYPES.get(i));
			}
			int typeNum = -1;
			while (typeNum < 0 || typeNum >= Headphone.HEADPHONE_TYPES.size()) {
				System.out.print("Please enter a number corresponding to a type from the given options: ");
				if (input.hasNextInt()) {
					typeNum = input.nextInt();
				}
				input.nextLine();
			}
			String type = Headphone.HEADPHONE_TYPES.get(typeNum);
			// Get the headphone itself.
			System.out.println(type + " headphones:");
			Headphone[] list = HeadphoneIO.getHeadphoneList(type).getAll();
			for (int i = 0; i < list.length; i++) {
				System.out.println("" + i + ": " + list[i].getName());
			}
			int headphoneNum = -1;
			while (headphoneNum < 0 || headphoneNum >= list.length) {
				System.out.print("Please enter a number corresponding to a headphone from the given options: ");
				if (input.hasNextInt()) {
					headphoneNum = input.nextInt();
				}
				input.nextLine();
			}
			Headphone base = list[headphoneNum];
			
			// Get the array of decibel differences.
			System.out.println("Enter the difference in decibels (in positive or negative "
					+ "decimal values) from the base headphone you'd like for each frequency."); 
			double[] freqDiff = new double[Headphone.MEASURED_FREQUENCIES.length];
			for (int i = 0; i < freqDiff.length; i++) {
				System.out.print("" + Headphone.MEASURED_FREQUENCIES[i] + "hz: ");
				while (!input.hasNextDouble()) {
					System.out.println("Please enter a valid decimal value (positive or negative).");
					input.nextLine();
				}
				freqDiff[i] = input.nextDouble();
			}
			
			// Get the closest matching headphones.
			// Get the type of headphone to search for.
			System.out.println("Which type of headphone would you like to search for?");
			System.out.println("Types of headphones:");
			for (int i = 0; i < Headphone.HEADPHONE_TYPES.size(); i++) {
				System.out.println("" + i + ": " + Headphone.HEADPHONE_TYPES.get(i));
			}
			typeNum = -1;
			while (typeNum < 0 || typeNum >= Headphone.HEADPHONE_TYPES.size()) {
				System.out.print("Please enter a number corresponding to a type from the given options: ");
				if (input.hasNextInt()) {
					typeNum = input.nextInt();
				}
				input.nextLine();
			}
			type = Headphone.HEADPHONE_TYPES.get(typeNum);
			HeadphoneList compareList = HeadphoneIO.getHeadphoneList(type);
			// Get the number of headphones to return.
			System.out.println("How many closest matches would you like to return?");
			int returnAmt = -1;
			while (returnAmt < 0) {
				System.out.println("Please enter a positive integer.");
				if (input.hasNextInt()) {
					returnAmt = input.nextInt();
					returnAmt = returnAmt < compareList.size() ? returnAmt : compareList.size();
				}
				input.nextLine();
			}
			// Analyze the input and print out the closest matches.
			Headphone[] closestMatches = Analyzer.analyze(base, freqDiff, returnAmt, compareList.getAll());
			System.out.println("Closest matches:");
			for (Headphone headphone : closestMatches) {
				System.out.println(headphone.getName());
			}
			
			System.out.println("Thanks for using the headphone analzyer!");
		*/
	}
	
}
