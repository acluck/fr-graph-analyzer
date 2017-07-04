package parse;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.Test;

import data.Headphone;

/**
 * Tests the MeasurementParser class.
 * 
 * @author Adam Luck
 */
public class MeasurementParserTest {

	/**
	 * Tests all of MeasurementParser's methods.
	 * @throws InvalidPasswordException if the program doesn't have permissions to load the file.
	 * @throws IOException if the program has issues loading the file.
	 * @throws InvalidDocumentException if the PDF being parsed is invalid.
	 */
	@Test
	public void test() throws InvalidPasswordException, IOException, InvalidDocumentException {
		String path = "C:\\Users\\Adam\\Downloads\\AKGK7XX.pdf";
		Headphone headphone = new Headphone("AKG K7XX", "Full-Size-Open");
		MeasurementParser.parseMeasurements(path, headphone);
		double[] dBVals = headphone.getDBVals();
		for (int i = 0; i < dBVals.length; i++) {
			System.out.println("" + Headphone.MEASURED_FREQUENCIES[i] + ": " +  dBVals[i]);
		}
		
		path = "C:\\Users\\Adam\\Downloads\\StatusSMOB1.pdf";
		headphone = new Headphone("Status SMOB1", "Full-Size-Open");
		MeasurementParser.parseMeasurements(path, headphone);
		dBVals = headphone.getDBVals();
		for (int i = 0; i < dBVals.length; i++) {
			System.out.println("" + Headphone.MEASURED_FREQUENCIES[i] + ": " +  dBVals[i]);
		}
	}

}
