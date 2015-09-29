package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import utils.ValueComparator;
import index.Word;

public class Search {

	private Map<String, Double> documentCoefs;
	private List<String> words;
	private HashMap<String, Map<String, Double>> saltonCoefs;
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
				documentCoefs.put(documentName, nf.parse(tokenizer.nextToken())
						.doubleValue());
				document = false;
			} else {
				if (firstElement.equals("###")) {
					document = true;
				}
				word = firstElement;
				if (words.contains(word)) {
					System.out.println(documentName);
					ponderation += nf.parse(tokenizer.nextToken())
							.doubleValue();
					HashMap<String, Double> temp = new HashMap<String, Double>();
					double salton = calculateSaltonCoef(ponderation,
							documentName);
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
		hashstoString();
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
		return ponderation
				/ Math.sqrt(documentCoefs.get(documentName) * words.size());
	}

	private void hashstoString() {
		System.out.println("Document coefs : ");
		Iterator it = documentCoefs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
		System.out.println("Salton coefs : ");
		it = saltonCoefs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}

	private void sortHashMaps() {

		ValueComparator comparator = new ValueComparator(documentCoefs);
		TreeMap<String, Double> temp = new TreeMap<String, Double>(comparator);
		temp.putAll(documentCoefs);
		documentCoefs = temp;

		Iterator it = saltonCoefs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			comparator = new ValueComparator(
					(HashMap<String, Double>) pair.getValue());
			temp = new TreeMap<String, Double>(comparator);
			temp.putAll((HashMap<String, Double>) pair.getValue());
			Map<String, Double> hash = temp;
			saltonCoefs.put((String) pair.getKey(),
					(Map<String, Double>) hash);
			
		}
	}
}
