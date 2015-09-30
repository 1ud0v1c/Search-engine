package index;

import java.text.DecimalFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * La classe Document est la représentation d'un document en mémoire, elle est composée de son nom, des mots qui composent le document associé à leur pondération
 * ainsi que le calcul de la somme des pondérations au carré qui va permettre d'accélérer la recherche en effectuant le calcul par avant.
 * @author Lucie Lagarrigue 
 * @author Ludovic Vimont
 */
public class Document {
	private String documentName;
	private HashMap<Word, Double>  indexes;
	private double sumPonderationSquare;
	
	public Document(String documentName) {
		super();
		this.documentName = documentName;
		indexes = new HashMap<Word, Double>();
		sumPonderationSquare = 0;
	}
	
	public Document(String documentName, HashMap<Word, Double> indexes, double sumPonderationSquare) {
		super();
		this.documentName = documentName;
		this.indexes = indexes;
		this.sumPonderationSquare = sumPonderationSquare;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public void addIndex(Word word, double ponderation) {
		indexes.put(word, ponderation);
	}
	
	/**
	 * La fonction containsWord permet de vérifier si un mot existe déjà dans la HashMap du document
	 * @param name : le mot dont on veut vérifier la présence
	 * @return si le mot existe, on le retourne sinon on renvoit null.
	 */
	public Word containsWord(String name) {
		Set<Word> words = indexes.keySet();
			
		for (Word w : words) {
			if(w.getName().equals(name)) {
				return w;
			}
		}
		return null;
	}
	
	public HashMap<Word, Double> getIndexes() {
		return indexes;
	}
	
	public void setIndexes(HashMap<Word, Double> indexes) {
		this.indexes = indexes;
	}
	
	public double getSumPonderationSquare() {
		return sumPonderationSquare;
	}
	
	public void setSumPonderationSquare(double sumPonderationSquare) {
		this.sumPonderationSquare = sumPonderationSquare;
	}
	
	public String toString() {
		String str = "Document name : +"+documentName+" \n";
		Iterator it = indexes.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Word word = ((Word)pair.getKey());
	        str += "Mot : \""+word.getName()+ "\", occurence : "+word.getOccurences().get(documentName)+", value = " + new DecimalFormat("##.##").format(pair.getValue())+"\n";
	    }
	    str += "\n\n";
	    return str;
	}
}
