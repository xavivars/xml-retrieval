package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author 
 *
 */
public class WordsFrequencyMap extends HashMap < String, Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param newToken
	 */
	protected void addWord (String newToken) {
		if (containsKey(newToken)) {
			Integer freq = get(newToken);
			freq++;
			put(newToken, freq);
		}
		else {
			put(newToken,new Integer(1));
		}
	}
	
	/**
	 * 
	 *
	 */
	public final void print(int limit) {
		Set keySet = this.keySet();
		Iterator it = keySet.iterator();
		
		int nStopWords = 0;
		while (it.hasNext()) {
			String key = (String)it.next();
			Integer value = (Integer)this.get(key);
			if (value <= limit) {
			System.out.println(key + " (" + value + ")");
			nStopWords++;
			}
		}
		
		System.out.println("Stop words: " + nStopWords);
	}
	
}
