package utils;

import index.Document;
import index.Word;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class HandleFile {
	private String fileName;

	public HandleFile(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	public Document fillDocument(HashMap<String, Integer> allWords, LinkedList<String> stopList) throws IOException {
		Document doc = new Document(fileName);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String line = null;
		
		while( (line = br.readLine())!= null ){
			line = line.replaceAll("\\p{Punct}+", " ").toLowerCase();			
			String [] tokens = line.split("\\s+");
			for (String tokenWord : tokens) {
				if(tokenWord.length() > 1 && !stopList.contains(tokenWord)) {
					Word w = doc.containsWord(tokenWord);
					if(w != null) {
						w.setWordWithOccurence(fileName);
						doc.addIndex(w, 0);
					} else {
						Word newWord = new Word(tokenWord, fileName);
						newWord.setWordWithOccurence(fileName);
						doc.addIndex(newWord, 0);

						int increment = (allWords.get(tokenWord) != null) ? allWords.get(tokenWord)+1 : 1;
						allWords.put(tokenWord, increment);
					}
				}
			}
		}
		br.close();
		return doc;
	}
	
	public void writeIndex(LinkedList<Document> docs) throws IOException {
		PrintWriter file = new PrintWriter(new FileWriter(fileName));
		for (Document doc : docs) {
			System.out.println(doc.getDocumentName());
			file.println(doc.getDocumentName()+" "+new DecimalFormat("##.##").format(doc.getSumPonderationSquare()));
			HashMap<Word, Double> indexes = doc.getIndexes();
			Iterator it = indexes.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Word word = ((Word) pair.getKey());
				file.println(word.getName()+" "+new DecimalFormat("##.##").format(pair.getValue()));
			}
			file.println("###");
		}
		file.close();
	}

	public LinkedList<String> getWords() throws IOException {
		LinkedList<String> stopList = new LinkedList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String line = null;
		
		while( (line = br.readLine())!= null ){
			stopList.add(line);
		}
		br.close();
		return stopList;
	}


}
