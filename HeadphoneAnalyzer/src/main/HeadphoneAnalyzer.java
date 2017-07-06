package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
	
		loadCurrentHeadphones();
		
		loadNewHeadphones();
		
		// TODO Use Analyzer to analyze user input and give headphone recommendations.
		
		saveNewHeadphones();
	}

	/**
	 * Loads the headphone information for all measurements that have already been parsed.
	 */
	private void loadCurrentHeadphones() {
		// TODO Check Headphones folder for .txt files containing previously processed headphones.
		// 		Add all headphones to the list.
	}
	
	/**
	 * Checks Innerfidelity for any new measurement sheets that have been uploaded.
	 */
	private void loadNewHeadphones() {
		
		try {
			Document doc = Jsoup.connect(measurementsURL).get();
			for (int i = 4; i < headphones.length; i++) {
				String type = headphones[i].getType();
				String typeSearch = type;
				if (typeSearch.equals("Earbud"))
					typeSearch = "Ear-bud";
				else if (typeSearch.equals("In-Ear"))
					typeSearch = "In-ear";
				else if (typeSearch.equals("Wireless"))
					typeSearch = "Wireless Headphones";
				Element section = doc.getElementsContainingOwnText(typeSearch).parents().first();
				Elements elements = section.select("a");
				for (Element element : elements) {
					String name = element.text();
					name = name.replaceAll("w/", "with");
					name = name.replaceAll("[\\/:*?\"<>|]", "");
					String link = element.absUrl("href");
					link = link.replaceFirst("http", "https");
					link = link.replaceAll(".pdf.pdf", ".pdf");
					String directoryPath = "./Headphones/" + type + "/" + name;
					File directory = new File(directoryPath);
					if (!directory.isDirectory() && !directory.mkdirs()) {
						throw new IOException("Failed to make headphone directory.");
					}
					String filePath = directoryPath + "/" + name + ".pdf";
		            URL docLink = new URL(link);
		            URLConnection connection = docLink.openConnection();
		            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); 
		            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
		            FileOutputStream fos = new FileOutputStream(filePath);
			        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		            rbc.close();
		            fos.close();
		            
		            Headphone headphone = new Headphone(name, type);
		            MeasurementParser.parseMeasurements(filePath, headphone);
		            headphones[i].add(headphone);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Use MeasurementParser to set information for new headphones
	}

	/**
	 * Saves all headphone measurements that don't already exist.
	 */
	private void saveNewHeadphones() {
		for (HeadphoneList list : headphones) {
			String type = list.getType();
			for (Headphone headphone : list.getAll()) {
				String name = headphone.getName();
				Path path = Paths.get("./Headphones/" + type + "/" + name + "/" + name + ".txt");
				File measurementFile = path.toFile();
				if (!measurementFile.exists()) {
					saveHeadphone(headphone, path);
				}
			}
		}
	}

	/**
	 * Saves the measurement information of the given headphone in a text file.
	 * @param headphone Headphone with measurements to save.
	 * @param path Path of the file to save the information in.
	 */
	private void saveHeadphone(Headphone headphone, Path path) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(path.toString());
			bw = new BufferedWriter(fw);
			bw.write(headphone.getName() + "\n");
			double[] dBVals = headphone.getDBVals();
			for (int i = 0; i < dBVals.length; i++) {
				bw.write(Double.toString(dBVals[i]) + " ");
			}
			bw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}

}
