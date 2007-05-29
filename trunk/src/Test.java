/*
 * Copyright (C) 2007
 *  
 * Authors:
 *  Enrique Benimeli Bofarull <ebenimeli@gmail.com>
 *  David Ortega Parilla <dortegaparrilla@gmail.com>
 *  Xavier Ivars i Ribes <xavi@infobenissa.com>
 *  
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

import index.IndexFactory;
import index.StopWordFactory;
import query.QueryManager;
import query.WordResultList;
import utils.StopWordMap;
import xml.StopWordReader;

/**
 * 
 * @author 
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// filtramos word-freqs.xml
		final StopWordReader swr = new StopWordReader("word-freqs.xml");
		swr.setBottomLimit(100000);
		swr.removeSize(new Integer(1));
		swr.removeSize(new Integer(2));
		swr.removeSize(new Integer(3));
		final StopWordMap swm = swr.readDocument();
		swm.printXML("stop-words-final.xml");

		final StopWordReader swr2 = new StopWordReader("stop-words-final.xml");
		final StopWordMap swm2 = swr2.readDocument();
		// Index
		final IndexFactory factory = new IndexFactory();
		factory.index(args[0], swm2);
		
		
		// Query
		/*QueryManager queryManager = new QueryManager();
		queryManager.setStopWordMap(swm);
		String queryFileName = "./topics/query001.xml";
		WordResultList wordResultList = queryManager.processQuery(queryFileName);
		wordResultList.print();
		*/
		
	}

	/**
	 * 
	 *
	 */
	private final void stopWords() {
		// Crea un archivo con palabra-frecuencia
		// Se insertan todas

		//StopWordFactory swf = new StopWordFactory("word-freqs.xml");
		//swf.getStopWords(args[0]);
		
	
	}

}
