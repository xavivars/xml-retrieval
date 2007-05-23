package utils;


import java.util.StringTokenizer;

public class WordCounter extends XMLReader {
	
	private WordsFrequencyMap wordsFrequency; 

	protected WordCounter(String fileName, WordsFrequencyMap wf) {
		super(fileName);
		setWordsFrequency(wf);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	protected void tokenize(final String text, final String document,
			final String path) {
		StringTokenizer tokens = new StringTokenizer(text, " ");
		while (tokens.hasMoreElements()) {
			String token = tokens.nextToken();
			String newToken = formatToken(token);
			if (newToken != null) {
				getWordsFrequency().addWord(newToken);
			} else {
				//System.out.println("'" + token + "' is not a valid token");
			}
		}
	}


	public WordsFrequencyMap getWordsFrequency() {
		return wordsFrequency;
	}


	public void setWordsFrequency(WordsFrequencyMap wordsFrequency) {
		this.wordsFrequency = wordsFrequency;
	}
}
