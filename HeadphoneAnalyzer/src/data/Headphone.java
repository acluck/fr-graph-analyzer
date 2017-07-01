package data;

import java.awt.image.BufferedImage;

/**
 * Object containing information about a headphone.
 * Includes name, type, associated image, and an
 * array of values corresponding to frequency values (20-10k).
 * 
 * @author Adam Luck
 */
public class Headphone {

	/** Name of the headphone */
	private String name;
	/** Type of headphone (OB, CB, or IEM) */
	private String type;
	/** Image of the headphone's measurements */
	private BufferedImage image;
	/** Array of measured frequency values */
	private int[] frequencies;
	/** Array of hz values that the frequencies array corresponds to **/
	static final int[] MEASURED_FREQUENCIES = new int[] {
			20, 30, 40, 50, 60, 70, 80, 90, 100,
			200, 300, 400, 500, 600, 700, 800, 900, 1000,
			2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000
		};
	
	/**
	 * Headphone objects are constructed in PDFParser, which sets the
	 * name, type, and image for the headphone.
	 * @param name Name of the headphone.
	 * @param type Type of headphone.
	 * @param image Image of the headphone's measurements.
	 */
	public Headphone(String name, String type, BufferedImage image) {
		this.name = name;
		this.type = type;
		this.image = image;
	}
	
	/**
	 * Returns the image of this headphone's measurements.
	 * Used by ImageParser.
	 * @return the image of this headphone's measurements.
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Frequencies are found and set through ImageParser.
	 * @param frequencies Array of measured frequency values.
	 */
	public void setFrequencies(int[] frequencies) {
		this.frequencies = frequencies;
	}

	/**
	 * Returns the name of this headphone.
	 * Used by HeadphoneAnalyzer.
	 * @return the name of this headphone.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this headphone.
	 * Used by HeadphoneAnalyzer.
	 * @return the type of this headphone.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns this headphone's frequencies.
	 * Used by HeadphoneAnalyzer.
	 * @return this headphone's frequencies..
	 */
	public int[] getFrequencies() {
		return frequencies;
	}
	
}
