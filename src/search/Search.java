package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Search {

	private HashMap<String, Double> documentCoef;
	private List<String> words;
	private String indexFileName;

	public Search(String request, String inIndexFileName) {
		super();
		words = new ArrayList<>();
		indexFileName = inIndexFileName;
		cleanRequest(request);
		documentCoef = new HashMap<String, Double>();
	}

	public void search() throws IOException {
		readIndexes();
	}

	private void readIndexes() throws IOException {
		File indexFile = new File(indexFileName);
		FileReader reader = new FileReader(indexFile);
		BufferedReader br = new BufferedReader(reader);
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(currentLine);
			String word = tokenizer.nextToken();
			if (words.contains(word)) {
				double ponderation = Double.parseDouble(tokenizer.nextToken());
				String documentName = tokenizer.nextToken();
				documentCoef.putIfAbsent(documentName,
						Double.parseDouble(tokenizer.nextToken()));
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
}
