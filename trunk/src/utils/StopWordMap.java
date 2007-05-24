package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class StopWordMap extends HashMap<String, StopWord> {


	/**
	 * 
	 *
	 */
	public final void print() {
		Set keySet = this.keySet();
		Iterator it = keySet.iterator();
		
		int nStopWords = 0;
		while (it.hasNext()) {
			String key = (String)it.next();
			StopWord stopWord = (StopWord)this.get(key);
			System.out.println(stopWord.getValue() + " (" + stopWord.getFrequency() + ")");
			nStopWords++;
		}
		System.out.println("Stop words: " + nStopWords);
	}
}
