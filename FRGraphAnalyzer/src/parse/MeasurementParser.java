package parse;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

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
	 * parseImage to get an array of decibel values.
	 * Creates and returns a new Headphone object.
	 * @param path Path to the PDF to parse.
	 * @param type Type of headphone to be created.
	 * @param url URL of the headphone's PDF.
	 * @throws InvalidPasswordException if the program doesn't have permissions to load the file.
	 * @throws IOException if the program has issues loading the file.
	 * @throws InvalidDocumentException if the PDF being parsed is invalid.
	 */
	public static Headphone parseMeasurements(String path, String type, String url)
			throws InvalidPasswordException, IOException, InvalidDocumentException {
		File file = new File(path);
		PDDocument document = PDDocument.load(file);
		String name = parseName(document);
		double[] dBVals = parseDBVals(document);
		document.close();
		return new Headphone(name, type, url, dBVals);
	}

	/**
	 * Parses the name from a headphone measurement PDF.
	 * @param document PDDocument to parse.
	 * @return the name of the headphone parsed from the document.
	 * @throws IOException if the program has issues loading the file.
	 */
	private static String parseName(PDDocument document) throws IOException {
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(document);
		String name = text.substring(text.indexOf("reserved.") + 11, text.indexOf("%") - 2);
		name = name.replaceAll("w/", "with");
		name = name.replaceAll("[\\/:*?'\"<>|]", " ");
		name = name.trim().replaceAll("\\s{2,}", " ");
		return name;
	}

	/**
	 * Parses the decibel values from a headphone measurement PDF.
	 * @param document PDDocument to parse.
	 * @throws InvalidDocumentException if the PDF being parsed is invalid.
	 * @throws IOException if the program has issues loading the file.
	 * @return the array of decibel values parsed from the document.
	 */
	private static double[] parseDBVals(PDDocument document)
			throws InvalidDocumentException, IOException {
		
		// Render the document/image.
		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(0, 350);
		
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
			boolean foundRed = false;
			boolean foundBlue = false;
			int finalRedY = bot;
			int finalBlueY = bot;
			// Get most red/blue pixel at the given x-coordinate between top and bottom
			int mostRed = 0;
			int mostBlue = 0;
			// Start at the top of the graph and work down.
			for (int yVal = top; yVal < bot; yVal++) {
				Color color = new Color(image.getRGB(xVals[i], yVal));
				int red = color.getRed();
				int blue = color.getBlue();
				// Don't count grayscale pixels.
				if (red == blue && red == color.getGreen()) {
					continue;
				}
				else if (red > mostRed && red > blue) {
					foundRed = true;
					mostRed = red;
					finalRedY = yVal;
					if (mostRed == Color.RED.getRed())
						break;
				}
				else if (blue > mostBlue && blue > red) {
					foundBlue = true;
					mostBlue = blue;
					finalBlueY = yVal;
					if (mostBlue == Color.BLUE.getBlue())
						break;
				}
			}
			
			// Calculate the final Y value based on the red and blue y values found.
			int finalYVal = 0;
			if (foundRed && foundBlue) {
				finalYVal = (finalRedY + finalBlueY) / 2;
			}
			else if (foundRed) {
				finalYVal = finalRedY;
			}
			else if (foundBlue) {
				finalYVal = finalBlueY;
			} else {
				throw new InvalidDocumentException("Couldn't parse measurements.");
			}
			
			// Do some math to calculate the decibel value using the graph proportions.
			int distFromBot = bot - finalYVal;
			double propDistFromBot = (double) distFromBot / botTopRange;
			double dBFromBot = propDistFromBot * DB_RANGE;
			double totalDB = dBFromBot + DB_MIN;
			dBVals[i] = totalDB;
		}
		return dBVals;
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
		return color.equals(Color.WHITE);
	}
	
	
}
