package util;

import java.util.Arrays;

import data.Headphone;

/**
 * Maintains a sorted list of all headphones that have been processed.
 * 
 * @author Adam Luck
 */
public class HeadphoneList {

	/** Array containing the headphones in the list. */
	private Headphone[] list;
	/** Initial list capacity. */
	private final int INIT_SIZE = 1000;
	/** Number of items in the list. */
	private int size;
	
	/**
	 * Constructor for the list.
	 */
	public HeadphoneList() {
		list = new Headphone[INIT_SIZE];
		size = 0;
	}
	
	/**
	 * Adds a headphone to the list in sorted order.
	 * @param headphone headphone to add to the list.
	 */
	public void add(Headphone headphone) {
		// Go backwards through the list until the proper index to add at is found.
		int index = size;
		while (index >= 0 && list[index - 1] != null
				&& headphone.compareTo(list[index - 1]) <= 0) {
			index--;
		}
		// Shift the list.
		for (int i = size; i > index; i--) {
			list[i] = list[i - 1];
		}
		// Add the headphone to the list.
		list[index] = headphone;
		size++;
		// Grow the list if necessary.
		if (size >= list.length) {
			list = Arrays.copyOf(list, list.length * 2);
		}
	}
	
	/**
	 * Returns the headphone with the given name if there is one in the list,
	 * and returns null otherwise.
	 * @param name Name of the headphone to check for.
	 * @return the headphone with the given name if there is one in the list.
	 */
	public Headphone get(String name) {
		// If the list is empty.
		if (size == 0) {
			return null;
		}
		// Use a binary search to find the headphone with the given name.
		int lowIdx = 0;
		int highIdx = size - 1;
		while (lowIdx <= highIdx) {
			int midIdx = (lowIdx + highIdx) / 2;
			if (list[midIdx] == null) {
				return null;
			}
			int comparison = name.compareTo(list[midIdx].getName());
			if (comparison == 0) {
				return list[midIdx];
			}
			else if (comparison < 0) {
				highIdx = midIdx - 1;
			}
			else {
				lowIdx = midIdx + 1;
			}
		}
		// Return null if the headphone isn't in the list.
		return null;
	}
}
