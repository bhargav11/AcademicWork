package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		if (docFile == null)
			throw new FileNotFoundException();

		HashMap<String, Occurrence> answer = new HashMap<String, Occurrence>();

		Scanner sc = new Scanner(new File(docFile));

		while (sc.hasNext())
		{
			String key = getKeyword(sc.next());
			if (key != null)
			{
				if (answer.containsKey(key))
				{
					Occurrence occurance = answer.get(key);
					occurance.frequency++;
				}
				else
				{
					Occurrence oc = new Occurrence(docFile, 1);
					answer.put(key, oc);
				}
			}
		}
		sc.close();
		return answer;
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		for (String key : kws.keySet())
		{
			ArrayList<Occurrence> oc = new ArrayList<Occurrence>();

			if (keywordsIndex.containsKey(key))
				oc = keywordsIndex.get(key);
			
			oc.add(kws.get(key));
			insertLastOccurrence(oc);
			keywordsIndex.put(key, oc);
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */

	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		//strip trailing punctuation
		int n = 1;
		String output = "";
		word = word.toLowerCase();
		int j = 0;
		while(j<word.length())
		{
			if(Character.isLetter(word.charAt(j)))
			{
				
			}
			else
			{
				//return null;
			}
			j++;
		}
		
		while(n<word.length())
		{
			
			if(     word.charAt(word.length()-n) == '.' ||
					word.charAt(word.length()-n) == ',' ||
					word.charAt(word.length()-n) == '?' || 
					word.charAt(word.length()-n) == ':' ||
					word.charAt(word.length()-n) == ';' ||
					word.charAt(word.length()-n) == '!' )
			{
				output = word.substring(0, word.length()-n);
			}
			else
			{
				break;
			}
			n++;
		}
		if(     !(word.charAt(word.length()-1) == '.' ||
				word.charAt(word.length()-1) == ',' ||
				word.charAt(word.length()-1) == '?' || 
				word.charAt(word.length()-1) == ':' ||
				word.charAt(word.length()-1) == ';' ||
				word.charAt(word.length()-1) == '!' )
				)
		{
			output = word.substring(0, word.length());
		}
		// check if there are only alphanumeric characters
		
		ArrayList<String> items = new ArrayList<>();
		
		
	      try {
	        	
	            Scanner scanner = new Scanner(new File("noisewords.txt"));
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                items.add(line);
	                
	            }
	            scanner.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	      int i = 0;
	      boolean ans = false;
	      while(i<items.size()) {
		     if(output.equals(items.get(i))) 
		     {
		    	ans = true;
		    	break;
		     }
		     else
		     {
		    	 ans = false;
		     }
		     i++;
	      }
	      //System.out.println(ans);
	      if(ans == true)
	      {
	    	  return null;
	      }
	   /*  int a = 0;
		while(a<items.size())
		{
			System.out.println(items.get(a));
			a++;
		}*/
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		if(output != null)
		{
			return output;
		}
		
		return null;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	
	private ArrayList<Integer> binarySearch(ArrayList<Integer> arraylist, int key, int min, int max)
	{
		ArrayList<Integer> mids = new ArrayList<Integer>(); 
	  
		while (max >= min)
		{
			int mid = (min + max) / 2;
	      
			mids.add(mid); 
	      
			if (arraylist.get(mid) <  key)
			{
				max = mid - 1;
			}
	      
			else if (arraylist.get(mid) > key )
			{
				min = mid + 1;
			}
	      
			else
			{
				break; 
			}
		}
	  
		return mids; 
	}
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Integer> array = new ArrayList<Integer>(); 
		
		for (int j = 0; j < occs.size()-1; j++)
		{
			array.add(occs.get(j).frequency); 
		}
		
		int value = occs.get(occs.size()-1).frequency; 
		
		ArrayList<Integer> result = binarySearch(array, value, 0, array.size()-1); 
		
		
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return result;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<Occurrence> ocArr1 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> ocArr2 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> combined = new ArrayList<Occurrence>();
		
		if (keywordsIndex.containsKey(kw1))
			ocArr1 = keywordsIndex.get(kw1);
		
		if (keywordsIndex.containsKey(kw2))
			ocArr2 = keywordsIndex.get(kw2);
		
		combined.addAll(ocArr1);
		combined.addAll(ocArr2);
		
		if (!(ocArr1.isEmpty()) && !(ocArr2.isEmpty()))
		{
			// Sort with preference for ocArr1
			for (int x = 0; x < combined.size()-1; x++)
			{
				for (int y = 1; y < combined.size()-x; y++)
				{
					if (combined.get(y-1).frequency < combined.get(y).frequency)
					{
						Occurrence temp = combined.get(y-1);
						combined.set(y-1, combined.get(y));
						combined.set(y,  temp);
					}
				}
			}

			// Remove duplicates
			for (int x = 0; x < combined.size()-1; x++)
			{
				for (int y = x + 1; y < combined.size(); y++)
				{
					if (combined.get(x).document == combined.get(y).document)
						combined.remove(y);
				}
			}
		}

		// Top 5
		while (combined.size() > 5)
			combined.remove(combined.size()-1);
		
		//System.out.println(combined);
		
		for (Occurrence oc : combined)
			results.add(oc.document);

		return results;
	
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
	
	}
}

