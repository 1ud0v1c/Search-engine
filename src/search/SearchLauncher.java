package search;

import java.io.IOException;

public class SearchLauncher {

	public static void main(String[] args) {
		Search search = new Search("dude lol", "indexTest.txt");
		try {
			search.search();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
