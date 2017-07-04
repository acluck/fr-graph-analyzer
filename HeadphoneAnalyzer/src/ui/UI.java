package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

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
		File file = new File("C:\\Users\\Adam\\Downloads\\AKGK7XX.pdf");
		PDDocument document = PDDocument.load(file);
		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(0, 600);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\600s.png"));
		document.close();
		
		file = new File("C:\\Users\\Adam\\Downloads\\StatusSMOB1.pdf");
		document = PDDocument.load(file);
		renderer = new PDFRenderer(document);
		image = renderer.renderImageWithDPI(0, 600);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\600.png"));
		document.close();
	}
	
}
