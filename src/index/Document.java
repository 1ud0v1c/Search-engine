package index;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
	
	public Word containsWord(String name) {
		Set<Word> words = indexes.keySet();
		System.out.println("--- KeySet");
		for (Word w : words) {
			System.out.println(w);
		}
		System.out.println("--- EndOfKeySet");
		
		for (Word w : words) {
			if(w.getName().equals(name)) {
				System.out.println(w);
				System.out.println(w.getOccurences().size());
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
	        str += ((Word)pair.getKey()).getName() + " = " + pair.getValue()+"\n";
	        it.remove(); 
	    }
	    str += "\n\n";
	    return str;
	}
}
