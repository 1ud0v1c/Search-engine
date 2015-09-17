package utils;

import index.Document;
import index.Word;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {
	private String file;

	public ReadFile(String file) {
		super();
		this.file = file;
	}
	
	public Document fillDocument() throws IOException {
		Document doc = new Document(file);
		System.out.println("--- Début lecture fichier : "+file+" ---");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		
		while( (line = br.readLine())!= null ){
			line = line.replaceAll("\\p{Punct}+", "").toLowerCase();			
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
					}
					System.out.println(w);
				}
			}
		}
		
		System.out.println("--- Fin lecture ---");
		return doc;
	}


}
