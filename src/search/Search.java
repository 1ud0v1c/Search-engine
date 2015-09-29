package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import utils.ValueComparator;

public class Search {

	private Map<String, Double> documentCoefs;
	private List<String> words;
	private HashMap<String, Map<String, Double>> saltonCoefs;
	private Map<String, Double> finalValues = null;
	private String indexFileName;

	public Search(String request, String inIndexFileName) {
		super();
		words = new ArrayList<>();
		indexFileName = inIndexFileName;
		cleanRequest(request);
		documentCoefs = new HashMap<String, Double>();
		saltonCoefs = new HashMap<String, Map<String, Double>>();
	}

	public void search() throws IOException, ParseException {
		readIndexes();
	}
	
	public void printResults() {
		if (finalValues != null) {
			Iterator it = finalValues.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				System.out.println(pair.getKey());
			}
		}
	}

	private void readIndexes() throws IOException, ParseException {
		boolean document = true;
		File indexFile = new File(indexFileName);
		FileReader reader = new FileReader(indexFile);
		BufferedReader br = new BufferedReader(reader);
		String currentLine, documentName = "";
		NumberFormat nf = NumberFormat.getInstance();
		double ponderation = 0.0;
		while ((currentLine = br.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(currentLine);
			String word;
			String firstElement = tokenizer.nextToken();
			if (document) {
				documentName = firstElement;
				documentCoefs.put(documentName, nf.parse(tokenizer.nextToken()).doubleValue());
				document = false;
			} else {
				if (firstElement.equals("###")) {
					document = true;
				}
				word = firstElement;
				if (words.contains(word)) {
					ponderation += nf.parse(tokenizer.nextToken()).doubleValue();
					HashMap<String, Double> temp = new HashMap<String, Double>();
					double salton = calculateSaltonCoef(ponderation, documentName);
					if (saltonCoefs.containsKey(documentName)) {
						saltonCoefs.get(documentName).put(word, salton);
					} else {
						temp.put(word, salton);
						saltonCoefs.put(documentName, temp);
					}

				}
			}
		}
		br.close();
		sortHashMaps();
	}

	private void cleanRequest(String request) {
		StringTokenizer tokenizer = new StringTokenizer(request, " ,;.:!?'()");
		while (tokenizer.hasMoreTokens()) {
			String temp = tokenizer.nextToken().toLowerCase();
			if (temp.length() > 1) {
				words.add(temp);
			}
		}
	}

	private double calculateSaltonCoef(double ponderation, String documentName) {
		return ponderation / Math.sqrt(documentCoefs.get(documentName) * words.size());
	}

	private void sortHashMaps() {
		ValueComparator comparator = new ValueComparator(documentCoefs);
		TreeMap<String, Double> temp = new TreeMap<String, Double>(comparator);
		temp.putAll(documentCoefs);
		documentCoefs = temp;

		Iterator it = saltonCoefs.entrySet().iterator();
		HashMap<String, Double> values = new HashMap<String, Double>();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String docName = (String) pair.getKey();
			HashMap<String, Double> value = (HashMap<String, Double>) pair.getValue();
			Iterator itInside = value.entrySet().iterator();
			while (itInside.hasNext()) {
				Map.Entry pairInside = (Map.Entry) itInside.next();
				values.put(docName, (double) pairInside.getValue());
			}
		}
		comparator = new ValueComparator(values);
		temp = new TreeMap<String, Double>(comparator);
		temp.putAll(values);
		finalValues = temp;
	}
	
	private void hashstoString() {
		System.out.println("Document coefs : ");
		printMap(documentCoefs);
		System.out.println("Salton coefs : ");
		printMap(saltonCoefs);
		System.out.println("Final result : ");
		printMap(finalValues);
	}

	private void printMap(Map map) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}
}
