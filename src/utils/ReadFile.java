package utils;

import index.Document;

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
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		while( (line = br.readLine())!= null ){
			line = line.replaceAll("\\p{Punct}+", "").toLowerCase();			
			String [] tokens = line.split("\\s+");
			for (String tokenWord : tokens) {
				if(tokenWord.length() > 1) {
					
				}
			}
		}

		return doc;
	}


}
