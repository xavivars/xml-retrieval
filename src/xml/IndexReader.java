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

package xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import query.Document;
import query.WordResult;
import query.WordResultList;
import utils.Word;
import utils.WordList;
import utils.WordMap;
import org.xml.sax.*;

/**
 * 
 * @author ebenimeli
 *
 */
public class IndexReader extends SAXReader {
	
	/**
	 * 
	 */
	private WordMap wordMap;
	
	/**
	 * 
	 */
	private String fileName;
	
        
        private String tempWord, tempDocument, tempReference;
        
        /**
         * Estructura donde almacenamos temporalmente los resultados. Se utiliza un hashmap para acceder rápidamente a los elementos
         */
        private HashMap < String, HashMap < String, ArrayList < String > > > tempResult;
        
        private HashMap < String, String > searchWords;
        
        private boolean value, document, reference;
        
	/**
	 * 
	 * @param i
	 */
	public IndexReader(String indexFile) {
            fileName = indexFile;	
            value = false;
            document = false;
            reference = false;
            tempResult = new HashMap < String, HashMap < String, ArrayList < String > > > ();
            searchWords = new HashMap < String, String > ();
            tempWord = "";
            tempDocument = "";
            tempReference = "";
       	}
	        
        public WordResultList search (WordList  wl) {
            for(Word word: wl) {
                tempResult.put(word.getValue(), new HashMap < String, ArrayList < String > > ());
                searchWords.put(word.getValue(),null);
            }
            getText(fileName);
            
            return buildResultList();
        }
        
        /**
         *
         */
        public WordResultList buildResultList () {
            WordResultList result = new WordResultList ();
            WordResult wr = new WordResult ();
            
            Iterator it1 = tempResult.entrySet().iterator();
            //Para todas las palabras que se han buscado                 
            while (it1.hasNext()) {
                Map.Entry < String, HashMap < String, ArrayList < String > > > e1 = (Map.Entry < String, HashMap < String, ArrayList < String > > > ) it1.next();
                
                wr.setName(e1.getKey()); // Escribimos la palabra
                                
                ArrayList < Document > documents = new ArrayList < Document > ();
                
                HashMap < String, ArrayList < String > > docHashMap = e1.getValue();
                Iterator it2 = docHashMap.entrySet().iterator();
                
                //Para todos los documentos de una palabra
                while (it2.hasNext()) {
                    Map.Entry < String, ArrayList < String > > e2 = (Map.Entry <String, ArrayList < String > >) it2.next();
                    Document doc = new Document ();
                    doc.setName(e2.getKey()); // Escribimos el documento
                    doc.setPaths(e2.getValue()); // Escribimos los paths
                    documents.add(doc); // Añado el documento al array
                }
                
                wr.setDocuments(documents); // Añado el array de documentos
            }
            
            result.add(wr); //Añado la palabra al resultado
            
            return result;
        }
        
	/**
	 * 
	 * @return
	 */
	private WordMap getWordMap() {
		return wordMap;
	}
	
	/**
	 * 
	 * @param wordMap
	 */
	private void setWordMap(WordMap wordMap) {
		this.wordMap = wordMap;
	}
        
        public void characters(final char[] c, final int start, final int length) {
                   
            if (length > 0) {
                try {
                    if (value) {
                        buffer.append(c, start, length);
                        tempWord += buffer.toString();
                        buffer = new StringBuilder (kStringBuilder);
                    }
                    else if (document && (searchWords.containsKey(tempWord))) {
                        buffer.append(c, start, length);
                        tempDocument += buffer.toString();
                        buffer = new StringBuilder (kStringBuilder);
                    }
                    else if (reference && (searchWords.containsKey(tempWord))) {
                        buffer.append(c, start, length);
                        tempReference += buffer.toString();
                        buffer = new StringBuilder (kStringBuilder);
                    }
                }
                catch (java.nio.BufferOverflowException x) {
                    System.err.println("Insufficient text buffer size");
                    System.exit(1);
                }
            }
    }

    public void startElement(final String uri, final String localName,
                             final String tag, final Attributes attributes) {
        if (tag.equals("value")) {
            value = true;
        }
        else if (tag.equals("doc")) {
            document = true;
        }
        else if (tag.equals("ref")) {
            reference = true;
        }
    }
    
    public void endElement(final String uri, final String localName, final String tag) { 
        
        if(value) {
            value = false;
        }
        //Añadimos el documento si hace falta
        else if(document) {
            document = false;
            if (searchWords.containsKey(tempWord)) {
                HashMap < String, ArrayList < String > > documents = tempResult.get(tempWord);
                if (!documents.containsKey(tempDocument)) {
                    documents.put(tempDocument, new ArrayList < String > ());
                    tempResult.put(tempWord,documents);
                }
            }
        }
        //Añadimos el path
        else if (reference) {
            reference = false;
            if (searchWords.containsKey(tempWord)) {
                ArrayList < String > paths = tempResult.get(tempWord).get(tempDocument);
                paths.add(tempReference);
            }
            tempDocument = "";
            tempReference = "";
        }
        
        //Terminamos la búsqueda
        if (tag.equals("word")) {
            tempWord = "";
            if (searchWords.containsKey(tempWord)) {
                searchWords.remove(tempWord);
                if (searchWords.isEmpty()) {
                    reader = null;
                }
            }
        }
    }   
}
