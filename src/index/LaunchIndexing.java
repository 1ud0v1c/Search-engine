package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import utils.ReadFile;

public class LaunchIndexing {
	public static void main(String[] args) throws IOException {
		LinkedList<Document> documents = new LinkedList<Document>();
		
		File[] files = new File("corpus").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	ReadFile rf = new ReadFile("corpus"+"/"+file.getName());
		    	try {
		    		Document doc = rf.fillDocument();
					documents.add(doc);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		    }
		}
		
		
	}
}
