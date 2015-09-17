package index;

import java.util.HashMap;

public class Document {
	private String documentName;
	private HashMap<Word, Double>  indexes;
	private double sumPonderationSquare;
	
	public Document(String documentName) {
		super();
		this.documentName = documentName;
		this.indexes = new HashMap<Word, Double>();
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
}
