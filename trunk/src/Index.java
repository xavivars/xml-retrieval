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
public class Index {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// filtramos word-freqs.xml
		System.out.println("Indexing...");
		StopWordReader swr2;
		IndexFactory factory;
		StopWordMap swm2;
		
		String sourceXMLDir = args[0];
		String stopWordsFile = args[1];
		String indexesDir = args[2];
		String sMaxSizeIndex = args[3];
		Integer iMaxSizeIndex = new Integer(sMaxSizeIndex);
		
		swr2 = new StopWordReader(stopWordsFile);
		swm2 = swr2.readDocument();
		// Index
		factory = new IndexFactory();
		factory.setOutputPath(indexesDir);
		factory.setMaxSizeIndex(iMaxSizeIndex);
		factory.index(sourceXMLDir, swm2);
		
		factory = null;
		swr2 = null;
		
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
