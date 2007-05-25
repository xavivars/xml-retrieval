package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author ebenimeli
 * 
 */
public class StopWordMap extends HashMap<String, StopWord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 */
	public final void print() {
		final Set keySet = keySet();
		final Iterator it = keySet.iterator();

		int nStopWords = 0;
		while (it.hasNext()) {
			final String key = (String) it.next();
			final StopWord stopWord = get(key);
			System.out.println(stopWord.getValue() + " ("
					+ stopWord.getFrequency() + ")");
			nStopWords++;
		}
		System.out.println("Stop words: " + nStopWords);
	}
}
