package utils;

import java.util.HashMap;

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
}
