package lse;

import java.io.FileNotFoundException;

public class LittleSearchEngineDriver {
	public static void main(String[] args) throws FileNotFoundException {
		/* Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter the FILE NAME: ");
		String file = sc.nextLine();
		while(file.length() == 0) {
			System.out.println("\nPlease enter a FILE NAME: ");
			file = sc.nextLine();
		}
		System.out.println("\nEnter the NOISE FILE NAME: ");
		String noise = sc.nextLine();
		while(noise.length() == 0) {
			System.out.println("\nPlease enter a FILE NAME: ");
			noise = sc.nextLine();
		}
		sc.close(); 
		*/
		LittleSearchEngine test = new LittleSearchEngine();
		test.loadKeywordsFromDocument("AliceCh1.txt");
		System.out.println(test.getKeyword("paraphrase;"));
		//test.makeIndex("docs.txt", "noisewords.txt");
		//System.out.println(test.top5search("alice", "rabbit"));
		
		//System.out.println(test.getKeyword(""));
		//System.out.println(test.loadKeywordsFromDocument("AliceCh1.txt").get(null));
		
	}
}
