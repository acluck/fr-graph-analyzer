package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.Headphone;
import parse.InvalidDocumentException;
import parse.MeasurementParser;
import util.HeadphoneList;

/**
 * This program gets headphone measurement PDFs from Innerfidelity.com,
 * parses the documents into individual graphs,
 * reads and stores information about the headphones from the graphs,
 * and provides information based on comparisons between measurements.
 * 
 * @author Adam Luck
 */
public class HeadphoneAnalyzer {

	/** URL of Innerfidelity measurement PDFs. */
	private static String measurementsURL = "https://www.innerfidelity.com/headphone-measurements";
	/** Array containing the HeadphoneLists for each type of headphone */
	private HeadphoneList[] headphones;
	
	/**
	 * Constructs an array of empty HeadphoneLists.
	 */
	public HeadphoneAnalyzer() {
		String[] types = Headphone.HEADPHONE_TYPES;
		headphones = new HeadphoneList[types.length];
		for (int i = 0; i < types.length; i++) {
			headphones[i] = new HeadphoneList(types[i]);
		}
	}
	
	/**
	 * Gets the measurement PDF and stores all of the headphone measurements.
	 */
	public void getHeadphoneMeasurements() {
		
		System.out.println("Loading current headphones...");
		loadCurrentHeadphones();
		System.out.println("Getting new headphones...");
		getNewHeadphones();
		
		// TODO Use Analyzer to analyze user input and give headphone recommendations.
		System.out.println("Saving new headphones...");
		saveNewHeadphones();
	}

	/**
	 * Loads the headphone information for all measurements that have already been parsed.
	 */
	private void loadCurrentHeadphones() {
		try {
			File root = new File("./Headphones");
			// Add headphones to each list from each corresponding directory.
			for (File typeDir : root.listFiles()) {
				// Get the HeadphoneList that matches the name of the directory.
				String type = typeDir.getName();
				HeadphoneList headphoneList = null;
				for (HeadphoneList list : headphones) {
					if (list.getType().equals(type)) {
						headphoneList = list;
						break;
					}
				}
				if (headphoneList == null)
					throw new IOException("Directory structure invalid.");
				// Add each headphone in the directory to the list.
				for (File headphoneDir : typeDir.listFiles()) {
					for (File file : headphoneDir.listFiles()) {
						if (file.getName().endsWith(".txt")) {
				            Headphone headphone = loadHeadphone(file);
				            if (headphone == null) {
				            	throw new IOException(file.getName() + " couldn't be loaded.");
				            }
				            headphoneList.add(headphone);
						}
					}
				}
	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Loads a headphone object using the information in the given file.
	 * @param measurementFile File to get information from.
	 * @return Headphone created from the measurement file.
	 */
	private Headphone loadHeadphone(File measurementFile) {
		Headphone headphone = null;
		Scanner scanner = null;
		try {
			scanner = new Scanner(measurementFile);
			String name = scanner.nextLine();
			String type = scanner.nextLine();
			headphone = new Headphone(name, type);
			int totalVals = Headphone.MEASURED_FREQUENCIES.length;
			double[] dBVals = new double[totalVals];
			for (int i = 0; i < totalVals; i++) {
				if (scanner.hasNextDouble())
					dBVals[i] = scanner.nextDouble();
				else
					throw new InvalidDocumentException();
			}
			headphone.setDBVals(dBVals);
		} catch (FileNotFoundException | InvalidDocumentException e) {
			System.err.println("Error loading headphone: " + measurementFile.getName());
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return headphone;
	}

	/**
	 * Checks Innerfidelity for any new measurement sheets that have been uploaded.
	 */
	private void getNewHeadphones() {
		// Connect to the page containing all of the measurement PDFs.
		Document doc = null;
		try {
			doc = Jsoup.connect(measurementsURL).get();
			if (doc == null)
				throw new IOException("URL of the measurements page is broken.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Add all new headphones of each type to their respective lists.
		for (HeadphoneList headphoneList : headphones) {
			String type = headphoneList.getType();
			// Get the specific element containing the correct type of headphones.
			String typeSearch = type;
			if (typeSearch.equals("Earbud"))
				typeSearch = "Ear-bud";
			else if (typeSearch.equals("In-Ear"))
				typeSearch = "In-ear";
			else if (typeSearch.equals("Wireless"))
				typeSearch = "Wireless Headphones";
			Element section = doc.getElementsContainingOwnText(typeSearch).parents().first();
			// Select all links to measurement PDFs in the selected section.
			Elements elements = section.select("a");
			// Make a new directory and Headphone object for each new measurement PDF.
			for (Element element : elements) {
				String name = element.text();
				name = name.replaceAll("w/", "with");
				name = name.replaceAll("[\\/:*?\"<>|]", " ");
				name = name.trim().replaceAll("\\s{2,}", " ");
				// If the headphone is already in the list, skip and continue to the next one.
				if (headphoneList.get(name) != null)
					continue;
				try {
					// Get the link and ensure it is formatted correctly.
					String downloadLink = element.absUrl("href");
					downloadLink = downloadLink.replaceFirst("http", "https");
					downloadLink = downloadLink.substring(0, downloadLink.lastIndexOf(".")) + ".pdf";
					// Make a directory (if necessary) to store associated documents in.
					String directoryPath = "./Headphones/" + type + "/" + name;
					File directory = new File(directoryPath);
					if (!directory.isDirectory() && !directory.mkdirs())
						throw new IOException("Failed to make headphone directory.");
					// Connect to the URL, download the PDF, and save it in the directory.
					String filePath = directoryPath + "/" + name + ".pdf";
					if (!new File(filePath).exists()) {
						downloadFile(downloadLink, filePath);
					}
		            // Make a new Headphone object, parse its measurements, and add it to the list.
		            Headphone headphone = new Headphone(name, type);
		            MeasurementParser.parseMeasurements(filePath, headphone);
		            headphoneList.add(headphone);
				} catch (IOException | InvalidDocumentException e) {
					System.err.println("Error getting headphone: " + name);
					System.err.println(e.getMessage());
					continue;
				}
			}
		}
	}

	/**
	 * Saves the file at the given downloadLink at the given filePath.
	 * @param downloadLink Link to the file to download.
	 * @param filePath Path to save the file to.
	 * @throws IOException If the program is unable to download or save the file.
	 */
	private void downloadFile(String downloadLink, String filePath) throws IOException {
        URL docLink = new URL(downloadLink);
        URLConnection connection = docLink.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0"); 
        ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        rbc.close();
        fos.close();
	}

	/**
	 * Saves all headphone measurements that don't already exist.
	 */
	private void saveNewHeadphones() {
		for (HeadphoneList list : headphones) {
			String type = list.getType();
			for (Headphone headphone : list.getAll()) {
				String name = headphone.getName();
				Path filePath = Paths.get("./Headphones/" + type + "/" + name + "/" + name + ".txt");
				File measurementFile = filePath.toFile();
				if (!measurementFile.exists()) {
					saveHeadphone(headphone, filePath);
				}
			}
		}
	}

	/**
	 * Saves the measurement information of the given headphone in a text file.
	 * @param headphone Headphone with measurements to save.
	 * @param filePath Path of the file to save the information in.
	 */
	private void saveHeadphone(Headphone headphone, Path filePath) {
		try {
			FileWriter fw = new FileWriter(filePath.toString());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(headphone.getName() + "\n");
			bw.write(headphone.getType() + "\n");
			double[] dBVals = headphone.getDBVals();
			for (int i = 0; i < dBVals.length; i++) {
				bw.write(Double.toString(dBVals[i]) + " ");
			}
			bw.write("\n");
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("Error with headphone: " + headphone.getName());
			System.err.println(e.getMessage());
		}
	}

}
