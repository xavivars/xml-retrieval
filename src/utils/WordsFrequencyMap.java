package utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	public void printXML(final String fileName, int limit) {
		BufferedOutputStream bos;
		FileOutputStream fos;
		DataOutputStream dos;
		Set keySet = this.keySet();
		Iterator it = keySet.iterator();
		
		
		try {
			fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);
			dos.writeBytes("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			dos.writeBytes("<stop-words>\n");
			
			int nStopWords = 0;
			
			while (it.hasNext()) {
				String key = (String)it.next();
				Integer value = (Integer)this.get(key);
				if (value <= limit) {
					dos.writeBytes("\t<word f=\"" + value + "\">" + key + "</word>\n");
					nStopWords++;
				}
			}
			
			dos.writeBytes("</stop-words>\n");
			fos = null;
			bos = null;
			dos.close();
			dos = null;
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception eg) {
			eg.printStackTrace();
		}
	}
	
	
}
