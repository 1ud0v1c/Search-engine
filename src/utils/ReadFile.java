package utils;

import index.Document;
import index.Word;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ReadFile {
	private String file;

	public ReadFile(String file) {
		super();
		this.file = file;
	}
	
	public Document fillDocument(HashMap<String, Integer> allWords) throws IOException {
		Document doc = new Document(file);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		
		while( (line = br.readLine())!= null ){
			line = line.replaceAll("\\p{Punct}+", " ").toLowerCase();			
			String [] tokens = line.split("\\s+");
			for (String tokenWord : tokens) {
				if(tokenWord.length() > 1) {
					Word w = doc.containsWord(tokenWord);
					if(w != null) {
						w.setWordWithOccurence(file);
						doc.addIndex(w, 0);
					} else {
						Word newWord = new Word(tokenWord, file);
						newWord.setWordWithOccurence(file);
						doc.addIndex(newWord, 0);

						int increment = (allWords.get(tokenWord) != null) ? allWords.get(tokenWord)+1 : 1;
						allWords.put(tokenWord, increment);
					}
				}
			}
		}
		
		return doc;
	}


}
