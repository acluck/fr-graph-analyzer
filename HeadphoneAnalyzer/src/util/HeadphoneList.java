package util;

import java.util.Arrays;

import data.Headphone;

/**
 * Maintains a sorted list of all headphones of a certain type that have been processed.
 * 
 * @author Adam Luck
 */
public class HeadphoneList {

	/** Type of headphones contained in the list. */
	private String type;
	/** Array containing the headphones in the list. */
	private Headphone[] list;
	/** Initial list capacity. */
	private final int INIT_SIZE = 2;
	/** Number of items in the list. */
	private int size;
	
	/**
	 * Constructor for the list.
	 * @param type Type of headphones contained in the list.
	 */
	public HeadphoneList(String type) {
		this.type = type;
		list = new Headphone[INIT_SIZE];
		size = 0;
	}
	
	/**
	 * Returns the type of headphones contained in the list.
	 * @return the type of headphones contained in the list.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Adds a headphone to the list in sorted order.
	 * @param headphone headphone to add to the list.
	 */
	public void add(Headphone headphone) {
		// Go backwards through the list until the proper index to add at is found.
		int index = size;
		while (index > 0 && list[index - 1] != null
				&& headphone.compareTo(list[index - 1]) < 0) {
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
	public Headphone getByName(String name) {
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
	
	/**
	 * Returns the headphone with the given url if there is one in the list,
	 * and returns null otherwise.
	 * @param link PDF URL of the headphone to check for.
	 * @return the headphone with the given url if there is one in the list.
	 */
	public Headphone getByURL(String url) {
		// Look through the links in linear time (since they aren't ordered).
		for (int i = 0; i < size; i++) {
			if (list[i].getURL().equals(url)) {
				return list[i];
			}
		}
		// Return null if the headphone isn't in the list.
		return null;
	}
	
	/**
	 * Returns the array of headphones represented by this list.
	 * @return the array of headphones represented by this list.
	 */
	public Headphone[] getAll() {
		return Arrays.copyOf(list, size);
	}
	
	/**
	 * Returns the number of headphones in the list.
	 * @return the number of headphones in the list.
	 */
	public int size() {
		return size;
	}
}
