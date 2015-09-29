package search;

import java.io.IOException;
import java.text.ParseException;

public class SearchLauncher {

	public static void main(String[] args) {
		Search search = new Search("font petit", "index.txt");
		try {
			search.search();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
