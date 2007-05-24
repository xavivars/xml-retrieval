package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author ebenimeli
 * 
 */
public class WordMap extends HashMap<String, WordList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param word
	 */
	public final void addWord(final Word word) {
		WordList wordList = null;
		if (containsKey(word.getValue())) {
			wordList = get(word.getValue());
			wordList.add(word);
		} else {
			//System.out.println("New word: " + word.getValue());
			wordList = new WordList();
			wordList.add(word);
			put(word.getValue(), wordList);
		}
	}
	
	/**
	 * 
	 *
	 */
		public final void print() {
			Set keySet = this.keySet();
			Iterator it = keySet.iterator();
			
			int nWords = 0;
			while (it.hasNext()) {
				String key = (String)it.next();
				WordList wordList = (WordList)this.get(key);
				wordList.print();
				nWords++;
			}
			System.out.println("Words: " + nWords);
		}
		
}
