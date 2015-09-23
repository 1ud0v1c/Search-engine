package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import index.Word;

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
			String word;
			if (tokenizer.countTokens() == 3){
				tokenizer.nextToken();
				documentName = tokenizer.nextToken();
				documentCoefs.put(documentName,
						Double.parseDouble(tokenizer.nextToken()));
			}else {
				word = tokenizer.nextToken();
				if (words.contains(word)) {
					ponderation += Double.parseDouble(tokenizer.nextToken());
					HashMap<String, Double> temp = new HashMap<String,Double>();
					double salton = calculateSaltonCoef(ponderation, documentName);
					temp.put(documentName, salton);
					saltonCoefs.put(word, temp);
				}
			}
		}
		br.close();
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
	
	private void hashstoString(){
		System.out.println("Document coefs : ");
		Iterator it = documentCoefs.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	    }
		System.out.println("Salton coefs : ");
		it = saltonCoefs.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	    }
	}
}
