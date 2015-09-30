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
	
	/**
	 * La fonction fillDocument permet de créer un document en fonction d'un nom de fichier donné
	 * @param allWords Une liste que l'on met à jour qui va nous permettre de compter le nombre d'occurences d'un mot dans tout les documents
	 * @param stopList Une stop-list qui permet d'élaguer le fichier index obtenu suite à l'indexation
	 * @return Le document remplit et prêt à être utilsé.
	 * @throws IOException
	 */
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
	
	/**
	 * La fonction writeIndex permet d'écrire le résultat des différents TF-IDF dans notre fichier final.
	 * @param docs L'ensemble des documents 
	 * @throws IOException
	 */
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

	/**
	 * La fonction getWords permet de récupérer toutes les lignes d'un fichier en lisant ce dernier ligne par ligne, ici on l'utilise pour
	 * chager la stop-list.
	 * @return Une liste de tout les lignes d'un fichier.
	 * @throws IOException
	 */
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
