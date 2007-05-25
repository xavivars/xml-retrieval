package utils;

import java.util.StringTokenizer;

import org.w3c.dom.Element;

import xml.XMLReader;

/**
 * 
 * @author
 * 
 */
public class WordCounter extends XMLReader {

	/**
	 * 
	 */
	private WordsFrequencyMap wordsFrequency;

	/**
	 * 
	 * @param fileName
	 * @param wf
	 */
	protected WordCounter(String fileName, WordsFrequencyMap wf) {
		super(fileName);
		setWordsFrequency(wf);
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	@Override
	protected void tokenize(final String text, final String document,
			final String path) {
		final StringTokenizer tokens = new StringTokenizer(text, " ");
		while (tokens.hasMoreElements()) {
			final String token = tokens.nextToken();
			final String newToken = formatToken(token);
			if (newToken != null) {
				getWordsFrequency().addWord(newToken);
			} else {
				// System.out.println("'" + token + "' is not a valid token");
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public final WordList readDocument() {
		try {
			final Element root = getDocument().getDocumentElement();
			final String path = "/article[1]";
			readChildren(root, path);
			// System.out.println("WordMap size: " + getWordMap().size());
		} catch (final NullPointerException npe) {
			npe.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public WordsFrequencyMap getWordsFrequency() {
		return wordsFrequency;
	}

	/**
	 * 
	 * @param wordsFrequency
	 */
	public void setWordsFrequency(WordsFrequencyMap wordsFrequency) {
		this.wordsFrequency = wordsFrequency;
	}
}
