package index;
import java.util.HashMap;

public class Word {
	private String name;
	private HashMap<String, Integer>  occurences;
	private int presenceInDocument;
	
	public Word(String name, HashMap<String, Integer> occurences, int presenceInDocument) {
		super();
		this.name = name;
		this.occurences = occurences;
		this.presenceInDocument = presenceInDocument;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<String, Integer> getOccurences() {
		return occurences;
	}
	public void setOccurences(HashMap<String, Integer> occurences) {
		this.occurences = occurences;
	}
	public int getPresenceInDocument() {
		return presenceInDocument;
	}
	public void setPresenceInDocument(int presenceInDocument) {
		this.presenceInDocument = presenceInDocument;
	}
}
