import query.Query;
import utils.StopWord;
import utils.StopWordMap;
import xml.QueryReader;

public class ListFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Query query;
		final StopWordMap stopWords = new StopWordMap();

		stopWords.put("and", new StopWord("and"));

		final QueryReader qr = new QueryReader(args[0], stopWords);

		qr.analize();
		query = qr.readDocument();
		query.print();

		/*
		 * String newToken; String[] temp; String finalPunct =
		 * "[\\.,?!;:\\]\\)\\}]"; String beginningPunct = "[\\(\\[\\{]"; String []
		 * test = {"h63la","adios9","634,","h.la","coma,","(parentesis,",","};
		 * 
		 * for(String current: test) { newToken = current.toLowerCase(); if
		 * (newToken.matches(beginningPunct + "\\w+") || newToken.matches("\\w+" +
		 * finalPunct) || newToken.matches(beginningPunct + "\\w+" +
		 * finalPunct)) { temp = newToken.split("\\W"); for (String t: temp) {
		 * if(t.compareTo(" ") != 0 && t.compareTo("") != 0) { newToken = t; } } }
		 * else if (!newToken.matches("\\d+") &&
		 * !newToken.matches("[\\w&&[^\\d]]+")) { newToken = null; }
		 * 
		 * System.out.println(newToken); }
		 */
	}
}
