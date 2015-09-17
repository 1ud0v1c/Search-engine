package index;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Word {
	private String name;
	private HashMap<String, Integer>  occurences = new HashMap<>();
	private int presenceInDocuments;
		
	public Word(String name, String file) {
		super();
		this.name = name;
		occurences.put(file, 0);
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

	public void setWordWithOccurence(String file) {
		System.out.println(occurences.size());
		int increment = occurences.get(file)+1;
		System.out.println("Value +1 : "+increment);
		occurences.put(file, increment);
	}
	
	public int getPresenceInDocument() {
		return presenceInDocuments;
	}
	public void setPresenceInDocument(int presenceInDocuments) {
		this.presenceInDocuments = presenceInDocuments;
	}
	
	public String toString() {
		String str = "Word : "+name+"\n";
		Iterator it = occurences.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        str += "Document : "+pair.getKey() + " : " + pair.getValue()+"\n";
	        it.remove(); 
	    }
	    return str;
	}
	
}
