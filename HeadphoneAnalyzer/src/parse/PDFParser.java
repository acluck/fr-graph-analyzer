package parse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

import data.Headphone;

/**
 * Takes PDF files and initializes Headphone objects,
 * setting the headphone name, type, and image.
 * 
 * @author Adam Luck
 */
public class PDFParser {

	public static void main(String args[]) throws InvalidPasswordException, IOException {
		File file = new File("C:\\Users\\Adam\\Downloads\\XiaomiPiston2.pdf");
		PDDocument document = PDDocument.load(file);
		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(0, 250);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\XiaomiPiston2.png"));
		document.close();
		
		file = new File("C:\\Users\\Adam\\Downloads\\AKGK7XX.pdf");
		document = PDDocument.load(file);
		renderer = new PDFRenderer(document);
		image = renderer.renderImageWithDPI(0, 250);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\AKGK7XX.png"));
		document.close();
		
		file = new File("C:\\Users\\Adam\\Downloads\\JPSLabsAbyss.pdf");
		document = PDDocument.load(file);
		renderer = new PDFRenderer(document);
		image = renderer.renderImageWithDPI(0, 250);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\JPSLabsAbyss.png"));
		document.close();
		
		file = new File("C:\\Users\\Adam\\Downloads\\StatusSMOB1.pdf");
		document = PDDocument.load(file);
		renderer = new PDFRenderer(document);
		image = renderer.renderImageWithDPI(0, 250);
		ImageIO.write(image, "PNG", new File("C:\\Users\\Adam\\Downloads\\StatusSMOB1.png"));
		document.close();
	}
	
	/**
	 * Parses a PDF file and fills the ArrayList of headphones with new Headphone objects.
	 * @param file File to parse.
	 * @param headphones ArrayList of headphones to be analyzed.
	 * @throws InvalidPasswordException if the program doesn't have permissions to load the file. 
	 * @throws IOException if the program has issues loading the file.
	 */
	public void parsePDF(File file, ArrayList<Headphone> headphones)
			throws InvalidPasswordException, IOException {
		PDDocument document = PDDocument.load(file);
		PDFRenderer renderer = new PDFRenderer(document);
	}
	
	
}
