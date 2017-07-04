package parse;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
		String path = "C:\\Users\\Adam\\Downloads\\XiaomiPiston2.pdf";
		Headphone headphone = new Headphone("Xiaomi Piston 2", "In-Ear");
		MeasurementParser.parseMeasurements(path, headphone);
		double[] dBVals = headphone.getDBVals();
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter("./Headphones/In-Ear/" + headphone.getName() + "/" + headphone.getName() + ".txt");
			bw = new BufferedWriter(fw);
			bw.write(headphone.getName() + "\n");
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
