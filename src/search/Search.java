package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import utils.ValueComparator;

public class Search {

	private Map<String, Double> documentCoefs;
	private List<String> wordsFromRequest;
	private HashMap<String, Map<String, Double>> saltonCoefs;
	private Map<String, Double> finalValues = null;
	private String indexFileName;

	public Search(String request, String inIndexFileName) {
		super();
		wordsFromRequest = new ArrayList<>();
		indexFileName = inIndexFileName;
		cleanRequest(request);
		documentCoefs = new HashMap<String, Double>();
		saltonCoefs = new HashMap<String, Map<String, Double>>();
	}

	public void search() throws IOException, ParseException {
		readIndexes();
	}

	public void printResults() {
		if (finalValues != null) {
			if (finalValues.isEmpty()) {
				System.out.println("Aucun résultat !");
			} else {
				Iterator it = finalValues.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					System.out.println(pair.getKey());
				}
			}
		}
	}

	private void readIndexes() throws IOException, ParseException {
		boolean document = true;
		File indexFile = new File(indexFileName);
		FileReader reader = new FileReader(indexFile);
		BufferedReader br = new BufferedReader(reader);
		String currentLine, documentName = "";
		NumberFormat nf = NumberFormat.getInstance();
		double ponderation = 0.0;
		// On lit le fichier contenant l'index
		while ((currentLine = br.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(currentLine);
			String word;
			String firstElement = tokenizer.nextToken();
			// si l'information est celle d'un document, on l'ajoute dans la map
			// des documents
			if (document) {
				documentName = firstElement;
				double value = nf.parse(tokenizer.nextToken()).doubleValue();
				//On rajoute un poids au document si jamais le titre comprend l'un des mots de la requête
				StringTokenizer tempToken = new StringTokenizer(documentName, "_/.");
				while(tempToken.hasMoreTokens()){
					if(wordsFromRequest.contains(tempToken.nextToken().toLowerCase())){
						value += 1000;
					}
				}
				documentCoefs.put(documentName, value);
				document = false;
			} else {
				// si l'information suivante est celle d'un document, on change
				// le booléen
				if (firstElement.equals("###")) {
					document = true;
				}
				word = firstElement;
				// si le mot trouvé dans l'index est l'un des mots de la
				// requête, on récupère sa pondération et on calcule le
				// coefficient de Salton
				if (wordsFromRequest.contains(word)) {
					ponderation += nf.parse(tokenizer.nextToken()).doubleValue();
					HashMap<String, Double> temp = new HashMap<String, Double>();
					double salton = calculateSaltonCoef(ponderation, documentName);
					if (saltonCoefs.containsKey(documentName)) {
						saltonCoefs.get(documentName).put(word, salton);
					} else {
						temp.put(word, salton);
						saltonCoefs.put(documentName, temp);
					}
				}
			}
		}
		br.close();
		sortHashMaps();
	}

	private void cleanRequest(String request) {
		//On récupère les différents mots de la requête et on les enregistre
		StringTokenizer tokenizer = new StringTokenizer(request, " ,;.:!?'()");
		while (tokenizer.hasMoreTokens()) {
			String temp = tokenizer.nextToken().toLowerCase();
			if (temp.length() > 1) {
				wordsFromRequest.add(temp);
			}
		}
	}

	private double calculateSaltonCoef(double ponderation, String documentName) {
		return ponderation / Math.sqrt(documentCoefs.get(documentName) * wordsFromRequest.size());
	}

	private void sortHashMaps() {
		ValueComparator comparator;
		TreeMap<String, Double> temp;
		
		double newCoeff= 0.0;
		//On récupère les résultats, on les met dans une liste selon le document et le coefficient, puis on les trie selon la valeur du coefficient
		Iterator it = saltonCoefs.entrySet().iterator();
		HashMap<String, Double> values = new HashMap<String, Double>();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String docName = (String) pair.getKey();
			HashMap<String, Double> value = (HashMap<String, Double>) pair.getValue();
			Iterator itInside = value.entrySet().iterator();
			while (itInside.hasNext()) {
				//On récupère uniquement le nom du document et le coefficient
				Map.Entry pairInside = (Map.Entry) itInside.next();
				newCoeff += (double)pairInside.getValue();
			}
			//On ajoute la pondération du document
			newCoeff += documentCoefs.get(docName);
			values.put(docName, newCoeff);
			newCoeff = 0.0;
		}
		//On trie les résultats à afficher et on les stocke
		comparator = new ValueComparator(values);
		temp = new TreeMap<String, Double>(comparator);
		temp.putAll(values);
		finalValues = temp;
	}

	private void hashstoString() {
		System.out.println("Document coefs : ");
		printMap(documentCoefs);
		System.out.println("Salton coefs : ");
		printMap(saltonCoefs);
		System.out.println("Final result : ");
		printMap(finalValues);
	}

	private void printMap(Map map) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}
}
