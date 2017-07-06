package data;

/**
 * Object containing information about a headphone.
 * Includes name, type, associated image, and an
 * array of values corresponding to frequency values (20-10k).
 * 
 * @author Adam Luck
 */
public class Headphone implements Comparable<Headphone> {

	/** Name of the headphone. */
	private String name;
	/** Type of headphone. */
	private String type;
	/** Array of types of headphones. */
	public static final String[] HEADPHONE_TYPES = new String[] {
			"Full-Size Open", "Full-Size Sealed", "Earpad Open", "Earpad Sealed",
			"In-Ear", "Earbud", "Noise-Cancelling", "Wireless"
		};
	/** Array of this headphone's decibel values at selected frequencies. */
	private double[] dBVals;
	/** Array of frequencies measured by the program; corresponds to the frequencies array. **/
	public static final int[] MEASURED_FREQUENCIES = new int[] {
			20, 30, 40, 50, 60, 70, 80, 90, 100,
			200, 300, 400, 500, 600, 700, 800, 900, 1000,
			2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000
		};
	
	/**
	 * Constructor for new headphones.
	 * @param name Name of the headphone.
	 * @param type Type of headphone.
	 */
	public Headphone(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Constructor for headphones that have been previously processed.
	 * @param name Name of the headphone.
	 * @param type Type of headphone.
	 * @param dBVals Array of measured decibel values.
	 */
	public Headphone(String name, String type, double[] dBVals) {
		this.name = name;
		this.type = type;
		this.dBVals = dBVals;
	}
	
	/**
	 * Frequencies are found and set in MeasurementParser.
	 * @param dBVals Array of measured decibel values.
	 */
	public void setDBVals(double[] dBVals) {
		this.dBVals = dBVals;
	}

	/**
	 * Returns the name of this headphone.
	 * @return the name of this headphone.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this headphone.
	 * @return the type of this headphone.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns this headphone's decibel values.
	 * @return this headphone's decibel values.
	 */
	public double[] getDBVals() {
		return dBVals;
	}

	/**
	 * Hashcode function for headphones.
	 * @return hashcode given by the name and type of the headphone.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Returns true if this headphone equals another.
	 * @param obj Other object to compare this to.
	 * @return true if this headphone equals another.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Headphone other = (Headphone) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * Returns String's compareTo() using the headphone's name, and type if necessary.
	 * Returns -1 if this headphone is lexicographically earlier,
	 * 0 if it is the same, and 1 if it is after.
	 * @param other Headphone to compare to.
	 * @return String's compareTo() using the headphone's name, and type if necessary.
	 */
	@Override
	public int compareTo(Headphone other) {
		return name.compareTo(other.name) == 0 ? name.compareTo(other.name) : type.compareTo(other.type);
	}
	
}
