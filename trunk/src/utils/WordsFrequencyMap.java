package utils;

import java.util.HashMap;

public class WordsFrequencyMap extends HashMap < String, Integer> {
	
	protected void addWord (String newToken) {
		if (containsKey(newToken)) {
			Integer freq = get(newToken);
			freq++;
		}
		else {
			put(newToken,new Integer(0));
		}
	}
	
}
