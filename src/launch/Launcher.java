package launch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import index.Document;
import index.Word;
import search.Search;
import utils.HandleFile;

public class Launcher {

	public static void main(String[] args) throws IOException {
		while (true) {
			System.out.println("Entrez r pour rechercher un ou plusieurs mots");
			System.out.println("Entrez i pour lancer l'indexation");
			System.out.println("Entrez q pour quitter");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				String request = input.readLine();
				if (request.equals("q")) {
					return;
				}
				if (request.equals("i")) {
					System.out.println("Entrez le nom du fichier où enregistrer l'index (sans extension): ");
					index(input.readLine());
				} else {
					System.out.println("Entrez le nom du fichier contenant l'index (sans extension): ");
					String indexFileName = input.readLine();
					System.out.println("Entrez la requête :");
					request = input.readLine();
					Search search = new Search(request, indexFileName+".txt");
					search.search();
					System.out.println("Résultats : ");
					search.printResults();
				}
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void index(String indexFileName) throws IOException {
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

		// Calcul de la pond�ration pour chaque mot par rapport aux autres
		// documents
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
				double currentValue = (Double) pair.getValue();
				sumPonderationSquare += (currentValue * currentValue);
			}
			doc.setSumPonderationSquare(sumPonderationSquare);
		}

		HandleFile hf = new HandleFile(indexFileName+".txt");
		hf.writeIndex(documents);

		// System.out.println("--------------------------------------- Liste des
		// mots ------------------------------------------------------");
		// Iterator it = allWords.entrySet().iterator();
		// while (it.hasNext()) {
		// Map.Entry pair = (Map.Entry) it.next();
		// System.out.println("Mot : " + pair.getKey() + " => pr�sence "
		// + pair.getValue());
		// }
	}

}