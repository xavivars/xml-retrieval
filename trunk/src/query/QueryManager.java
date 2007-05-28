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


package query;

import java.util.Iterator;
import java.util.Map;
import utils.ReferenceList;
import utils.RootIndexMap;
import utils.StopWordMap;
import utils.WordMap;
import xml.IndexReader;
import xml.QueryReader;
import xml.RootIndexReader;
import xml.StopWordReader;

/**
 * 
 * @author ebenimeli
 *
 */
public class QueryManager {

	/**
	 * 
	 */
	private StopWordMap stopWordMap;
	private String rootIndexFile;
	/**
	 * 
	 *
	 */
	public QueryManager() {
		
	}
	
	/**
	 * 
	 * @param stopWordMap
	 */
	public QueryManager(final String stopFile, final String rootFile) {
                stopWordMap = (new StopWordReader(stopFile).readDocument());
                rootIndexFile = rootFile;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public final WordResultList processQuery(final Query query) {
		WordResultList wordResultList = null;
		
		wordResultList = new WordResultList();
		
		RootIndexMap rootIndexMap = readRootIndexMap();
                searchInIndexes(rootIndexMap);
                
		return wordResultList;	
	}
	
	/**
	 * 
	 * @param queryFileName
	 * @return
	 */
	public final WordResultList processQuery(final String queryFileName) {
		final QueryReader queryReader = new QueryReader(queryFileName, getStopWordMap());
		final Query query = queryReader.readDocument();

		WordResultList wordResultList = processQuery(query);
		
		return wordResultList;

	}

	/**
	 * @return the stopWordMap
	 */
	public final StopWordMap getStopWordMap() {
		return stopWordMap;
	}

	/**
	 * @param stopWordMap the stopWordMap to set
	 */
	public final void setStopWordMap(StopWordMap stopWordMap) {
		this.stopWordMap = stopWordMap;
	}
	
	/**
	 * 
	 * @return
	 */
	private final RootIndexMap readRootIndexMap() {
		final RootIndexReader rootIndexReader = new RootIndexReader(rootIndexFile);
		RootIndexMap rootIndexMap = null;
		
		rootIndexMap = rootIndexReader.read();
		
		
		return rootIndexMap;	
	}

                       
	/**
	 * 
	 * @return
	 */
	private final WordMap readIndexMap(String indexFile) {
		final IndexReader indexReader = new IndexReader(indexFile);
		WordMap indexMap = null;
		indexMap = indexReader.read();
		return indexMap;	
	}
        
        /**
	 * 
	 * @return
	 */
	private final WordMap searchIndexMap (String indexFile, String word) {
		final IndexReader indexReader = new IndexReader(indexFile);
		WordMap indexMap = null;
		indexMap = indexReader.search(word);
		return indexMap;	
	}

	
	private final WordMap searchInIndexes (RootIndexMap rootIndexMap) {
            
               //Buscamos todas las palabras en los índices en los que aparecen
                Iterator it = rootIndexMap.entrySet().iterator();
                
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    String word = (String) e.getKey();
                    ReferenceList refList = (ReferenceList) e.getValue();
                    for (Integer ref: refList) {
                        searchIndexMap("index_" + ref + ".xml", word);
                    }
                }
                
        }
}
