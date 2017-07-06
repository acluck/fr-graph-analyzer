package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

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
	 * @throws IOException 
	 * @throws InvalidPasswordException 
	 */
	public static void main(String[] args) throws InvalidPasswordException, IOException {

		HeadphoneAnalyzer analyzer = new HeadphoneAnalyzer();
		analyzer.getHeadphoneMeasurements();

		/**
		File file = new File("./Headphones/Full-Size Open/AKG Quincy Jones Q701/AKG Quincy Jones Q701.pdf");
		PDDocument document = PDDocument.load(file);
		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(0, 350);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\mystery.png"));
		document.close();
		*/
	}
	
}
