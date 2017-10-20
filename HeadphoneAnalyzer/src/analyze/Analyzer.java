package analyze;

import java.util.Arrays;

import data.Headphone;

/**
 * Analyzes headphone measurement preferences and returns recommended headphones.
 * 
 * @author Adam Luck
 */
public class Analyzer {
	
	/** Array of frequencies measured by the program. **/
	public static final int[] MEASURED_FREQUENCIES = Headphone.MEASURED_FREQUENCIES;
	
	/**
	 * Returns an array of Headphones that most closely match the given parameters.
	 * @param base Base headphone to compare to.
	 * @param freqDiff Array of dB differences for each of the measured frequencies.
	 * @param returnAmt Number of headphones to return.
	 * @param compareList Array of headphones to compare to.
	 * @return an array of Headphones that most closely match the given parameters.
	 */
	public static Headphone[] analyze(Headphone base, double[] freqDiff, int returnAmt, Headphone[] compareList) {
		// Error checking.
		if (compareList.length < returnAmt) {
			throw new IndexOutOfBoundsException("Number of headphones to return must be "
					+ "less than the number of headphones in the list.");
		}
		if (freqDiff.length != MEASURED_FREQUENCIES.length) {
			throw new IndexOutOfBoundsException("Length of the array of frequency differences must"
					+ " match the total number of measured frequencies.");
		}
		// If a base headphone is given, set the array of optimal frequency values.
		int numMF = MEASURED_FREQUENCIES.length;
		double[] hpF;
		double[] optimalF;
		if (base != null) {
			hpF = base.getDBVals();
			optimalF = new double[numMF];
			for (int i = 0; i < numMF; i++) {
				optimalF[i] = hpF[i] + freqDiff[i];
			}
		}
		else {
			optimalF = freqDiff;
		}
		double optimalAve = calculateAverage(optimalF);
		// Make an array to hold the total squared difference of dB values,
		// and the corresponding array of headphones to return.
		double[] lSDArr = new double[returnAmt];
		Arrays.fill(lSDArr, -1);
		Headphone[] closestHeadphones = new Headphone[returnAmt];
		// Calculate the squared difference of each headphone in the hpList,
		// updating the lSDArr and closestHeadphones array as necessary.
		for (Headphone headphone : compareList) {
			if (headphone.equals(base))
				continue;
			double totalSD = 0;
			hpF = headphone.getDBVals();
			double hpAve = calculateAverage(hpF);
			double aveDiff = optimalAve - hpAve;
			for (int i = 0; i < numMF; i++) {
				double difference = optimalF[i] - hpF[i] - aveDiff;
				totalSD += difference * difference;
			}
			for (int i = 0; i < lSDArr.length; i++) {
				if (lSDArr[i] < 0 || totalSD < lSDArr[i]) {
					insertHeadphone(i, totalSD, lSDArr, headphone, closestHeadphones);
					break;
				}
			}
		}
		return closestHeadphones;
	}

	/**
	 * Returns the average value of all of the values in the array.
	 * @param values Array of values to average.
	 * @return the average value of all of the values in the array.
	 */
	private static double calculateAverage(double[] values) {
		double average = 0;
		for (double val : values) {
			average += val;
		}
		average /= values.length;
		return average;
	}

	/**
	 * Inserts the given totalLSD and headphone at position index in
	 * lSDArr and closestHeadphones respectively.
	 * @param index Index to insert at.
	 * @param totalSD Value to insert into lSDArr.
	 * @param lSDArr Array of least squared difference values.
	 * @param headphone Headphone to insert into closestHeadphones.
	 * @param closestHeadphones Array of headphones with the least squared difference values.
	 */
	private static void insertHeadphone(int index, double totalSD, double[] lSDArr,
			Headphone headphone, Headphone[] closestHeadphones) {
		for (int i = lSDArr.length - 1; i > index; i--) {
			lSDArr[i] = lSDArr[i - 1];
			closestHeadphones[i] = closestHeadphones[i - 1];
		}
		lSDArr[index] = totalSD;
		closestHeadphones[index] = headphone;
	}

}
