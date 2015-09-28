package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import utils.ReadFile;

public class LaunchIndexing {
	public static void main(String[] args) throws IOException {
		LinkedList<Document> documents = new LinkedList<Document>();
		HashMap<String, Integer> allWords = new HashMap<String, Integer>();
		
		File[] files = new File("corpus").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	ReadFile rf = new ReadFile("corpus"+"/"+file.getName());
		    	try {
		    		Document doc = rf.fillDocument(allWords);
					documents.add(doc);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		    }
		}
		
		for (Document doc : documents) {
			System.out.println(doc);
		}
		
		System.out.println("--------------------------------------- Liste des mots ------------------------------------------------------");
		Iterator it = allWords.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println("Mot : "+pair.getKey() + " => présence " + pair.getValue());
	    }
		
		
	}
}
