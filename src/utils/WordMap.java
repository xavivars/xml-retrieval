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
			// System.out.println("New word: " + word.getValue());
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
		final Set keySet = keySet();
		final Iterator it = keySet.iterator();

		int nWords = 0;
		while (it.hasNext()) {
			final String key = (String) it.next();
			final WordList wordList = get(key);
			wordList.print();
			nWords++;
		}
		System.out.println("Words: " + nWords);
	}

	/**
	 * 
	 * @param fileName
	 */
	public void printXML(final String fileName) {
		BufferedOutputStream bos;
		FileOutputStream fos;
		DataOutputStream dos;
		final Set keySet = keySet();
		final Iterator it = keySet.iterator();

		try {
			fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);
			dos.writeBytes("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			dos.writeBytes("<index>\n");

			int nWords = 0;

			while (it.hasNext()) {
				final String key = (String) it.next();
				final WordList wordList = get(key);
				wordList.printXML(dos);
				nWords++;
			}

			dos.writeBytes("</index>\n");
			dos.writeBytes("<!-- " + nWords + " words -->\n");
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
