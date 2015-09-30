package index;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * La classe Word est la représentation d'un mot en mémoire, elle est composé de son nom et du nom du document où 
 * elle se trouve associée à son nombre d'occurrences.
 * @author Lucie Lagarrigue 
 * @author Ludovic Vimont
 */
public class Word {
	private String name;
	private HashMap<String, Integer>  occurences = new HashMap<>();

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
		int increment = occurences.get(file)+1;
		occurences.put(file, increment);
	}
	
	public String toString() {
		String str = "Word : "+name+"\n";
		Iterator it = occurences.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        str += "Document : "+pair.getKey() + " : " + pair.getValue()+"\n";
	    }
	    return str;
	}
	
}
