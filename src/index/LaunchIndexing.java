package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import utils.HandleFile;

public class LaunchIndexing {
	public static void main(String[] args) throws IOException {
		int nbDocs = 0;
		LinkedList<Document> documents = new LinkedList<Document>();
		HashMap<String, Integer> allWords = new HashMap<String, Integer>();

		
		// Chargement de la stopList en m�moire
		HandleFile hfStopList = new HandleFile("antidico.txt");
		LinkedList<String> stopList = hfStopList.getWords();

		
		// Parcourt des fichiers et remplissage des Documents.
		File[] files = new File("corpus").listFiles();
		for (File file : files) {
			if (file.isFile()) {
				HandleFile hf = new HandleFile("corpus" + "/" + file.getName());
				nbDocs++;
				try {
					Document doc = hf.fillDocument(allWords, stopList);
					documents.add(doc);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Calcul de la pond�ration pour chaque mot par rapport aux autres documents
		for (Document doc : documents) {
			String docName = doc.getDocumentName();
			HashMap<Word, Double> indexes = doc.getIndexes();
			Iterator it = indexes.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Word word = ((Word) pair.getKey());
				int ocurrences = word.getOccurences().get(docName);
				double pij = ocurrences * Math.log(nbDocs / allWords.get(word.getName()));
				pair.setValue(pij);
			}
		}
		
		// Pr�-calcul pour la recherche d�pendant du document
		for (Document doc : documents) {
			double sumPonderationSquare = 0;
			HashMap<Word, Double> indexes = doc.getIndexes();
			Iterator it = indexes.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				double currentValue = (Double)pair.getValue();
				sumPonderationSquare += (currentValue*currentValue);
			}
			doc.setSumPonderationSquare(sumPonderationSquare);
		}
		
		HandleFile hf = new HandleFile("index.txt");
		hf.writeIndex(documents);

//		System.out.println("--------------------------------------- Liste des mots ------------------------------------------------------");
//		Iterator it = allWords.entrySet().iterator();
//		while (it.hasNext()) {
//			Map.Entry pair = (Map.Entry) it.next();
//			System.out.println("Mot : " + pair.getKey() + " => pr�sence "
//					+ pair.getValue());
//		}

	}
}
