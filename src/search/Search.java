package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Search {

	private HashMap<String, Double> documentCoefs;
	private List<String> words;
	private HashMap<String, HashMap<String, Double>> saltonCoefs;
	private String indexFileName;

	public Search(String request, String inIndexFileName) {
		super();
		words = new ArrayList<>();
		indexFileName = inIndexFileName;
		cleanRequest(request);
		documentCoefs = new HashMap<String, Double>();
		saltonCoefs = new HashMap<String, HashMap<String, Double>>();
	}

	public void search() throws IOException {
		readIndexes();
		
	}

	private void readIndexes() throws IOException {
		File indexFile = new File(indexFileName);
		FileReader reader = new FileReader(indexFile);
		BufferedReader br = new BufferedReader(reader);
		String currentLine, documentName = "";
		double ponderation = 0.0;
		while ((currentLine = br.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(currentLine);
			String word = tokenizer.nextToken();
			if (word.length() == 3){
				tokenizer.nextToken();
				documentName = tokenizer.nextToken();
				documentCoefs.putIfAbsent(documentName,
						Double.parseDouble(tokenizer.nextToken()));
			}
			if (words.contains(word)) {
				ponderation += Double.parseDouble(tokenizer.nextToken());
				HashMap<String, Double> temp = new HashMap<String,Double>();
				double salton = calculateSaltonCoef(ponderation, documentName);
				temp.putIfAbsent(documentName, salton);
				saltonCoefs.put(word, temp);
			}
		}
		br.close();
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
}
