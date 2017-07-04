package parse;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

import data.Headphone;

/**
 * Parses PDF/image files to set information for headphone objects.
 * 
 * @author Adam Luck
 */
public class MeasurementParser {
	
	/** Point to check to see if the image is stretched or not. */
	static final Point STRETCH_CHECK = new Point(395, 605);
	/** Total decibel range of frequency response graphs */
	static final int DB_RANGE = 70;
	/** Minimum decibel value of frequency response graphs */
	static final int DB_MIN = -50;
	/** Y-value to begin at when parsing frequency response graphs. (no stretch 20dB) */
	static final int NS_FR_TOP = 605;
	/** Y-value to begin at when parsing frequency response graphs. (stretch 20dB) */
	static final int S_FR_TOP = 624;
	/** Y-value to end at when parsing frequency response graphs. (no stretch -50dB) */
	static final int NS_FR_BOT = 1029;
	/** Y-value to end at when parsing frequency response graphs. (stretch -50dB) */
	static final int S_FR_BOT = 1030;
	/** Array of x-values to look at when parsing frequency response graphs. (no stretch) */
	static final int[] NS_FR_X_VALS = new int[] {
			472, 515, 546, 569, 589, 605, 620, 632, 644,
			718, 761, 792, 815, 835, 851, 866, 878, 890,
			964, 1007, 1038, 1061, 1081, 1097, 1113, 1124, 1136
	};
	/** Array of x-values to parse in the frequency response graph (stretch) */
	static final int[] S_FR_X_VALS = new int[] {
			477, 522, 555, 579, 599, 617, 632, 645, 657,
			735, 779, 812, 836, 857, 874, 889, 902, 914,
			991, 1037, 1068, 1093, 1114, 1131, 1145, 1159, 1171
	};
	
	/**
	 * Renders a PDF file as an image, which is then passed to and parsed by
	 * parseImage to update the given headphone's information.
	 * @param path Path to the PDF to parse.
	 * @param headphone Headphone represented by the PDF.
	 * @throws InvalidPasswordException if the program doesn't have permissions to load the file.
	 * @throws IOException if the program has issues loading the file.
	 * @throws InvalidDocumentException if the PDF being parsed is invalid.
	 */
	public static void parseMeasurements(String path, Headphone headphone)
			throws InvalidPasswordException, IOException, InvalidDocumentException {
		File file = new File(path);
		PDDocument document = PDDocument.load(file);
		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(0, 350);
		parseImage(image, headphone);
		document.close();
	}

	/**
	 * Parses an image and updates the given headphone's information.
	 * @param image Image to parse.
	 * @param headphone Headphone to set information for.
	 * @throws InvalidDocumentException if the PDF being parsed is invalid.
	 */
	private static void parseImage(BufferedImage image, Headphone headphone)
			throws InvalidDocumentException {
		// Determine whether the image is stretched or not.
		boolean stretched = checkStretch(image);
		// Set variables based on stretch.
		int top;
		int bot;
		int[] xVals;
		if (stretched) {
			top = S_FR_TOP;
			bot = S_FR_BOT;
			xVals = S_FR_X_VALS;
		}
		else {
			top = NS_FR_TOP;
			bot = NS_FR_BOT;
			xVals = NS_FR_X_VALS;
		}
		// Calculate decibel values for each frequency in xVals.
		double[] dBVals = new double[xVals.length];
		int botTopRange = bot - top;
		for (int i = 0; i < xVals.length; i++) {
			// Get most red pixel at the given x-coordinate between top and bottom
			int xVal = xVals[i];
			int finalYVal = bot;
			int mostRed = 0;
			// Start at the top of the graph and work down.
			for (int yVal = top; yVal < bot; yVal++) {
				Color color = new Color(image.getRGB(xVal, yVal));
				int red = color.getRed();
				// Don't count grayscale pixels.
				if (color.getBlue() == red && color.getGreen() == red) {
					continue;
				}
				else if (color.getRed() > mostRed) {
					mostRed = color.getRed();
					finalYVal = yVal;
					if (mostRed == Color.RED.getRed())
						break;
				}
			}
			// Do some math to calculate the decibel value using the graph proportions.
			int distFromBot = bot - finalYVal;
			double propDistFromBot = (double) distFromBot / botTopRange;
			double dBFromBot = propDistFromBot * DB_RANGE;
			double totalDB = dBFromBot + DB_MIN;
			dBVals[i] = totalDB;
		}
		headphone.setDBVals(dBVals);
	}

	/**
	 * Returns whether the image is stretched.
	 * @param image Image to check.
	 * @return whether the image is stretched.
	 * @throws InvalidDocumentException if the PDF being parsed is invalid.
	 */
	private static boolean checkStretch(BufferedImage image)
			throws InvalidDocumentException {
		// Check where the corner of the frequency response graph should be.
		Color color = new Color(image.getRGB(STRETCH_CHECK.x, STRETCH_CHECK.y));
		// If it's white, the image is stretched.
		if (color.equals(Color.WHITE))
			return true;
		// If it's black, the image is not stretched.
		if (color.equals(Color.BLACK))
			return false;
		// If it's neither, the document isn't valid.
		throw new InvalidDocumentException();
	}
	
	
}
