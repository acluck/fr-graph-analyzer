package parse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Parses the frequency response graph from an Innerfidelity measurement PDF.
 * 
 * @author Adam Luck
 */
public class GraphParser {

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
}
