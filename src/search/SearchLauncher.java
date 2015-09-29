package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class SearchLauncher {

	public static void main(String[] args) {
		while (true){
			System.out.println("Entrez la requête (q pour quitter) : ");
			BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
			try {
				String request = entree.readLine();
				if(request.equals("q")){
					return;
				}
				Search search = new Search(request, "index.txt");
				search.search();
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}	
		}
	}

}
