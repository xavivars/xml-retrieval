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

package index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import utils.RootIndexMap;
import utils.StopWordMap;
import utils.WordCounter;
import utils.WordList;
import utils.WordMap;
import utils.WordsFrequencyMap;
import xml.DocumentReader;


/**
 * 
 * @author
 * 
 */
public class IndexFactory {

	/**
	 * 
	 */
	private int maxSizeIndex;
	
	/**
	 * Directory where 'root_index.xml' and 'index_i.xml' will be stored.
	 */
	private String outputPath;
	
	/**
	 * 
	 * 
	 */
	public IndexFactory() {
		 setOutputPath("");
		 setMaxSizeIndex(10);
	}
	
	/**
	 * 
	 * @param outputPath
	 */
	public IndexFactory(final String outputPath) {
		setOutputPath(outputPath);
		 setMaxSizeIndex(10);
	}

	/**
	 * 
	 * @param dir
	 * @return
	 */
	public ArrayList<File> readDirectory(File dir) {
		File[] childs;
		String name;
		final ArrayList<File> list = new ArrayList<File>();

		if (dir.isDirectory()) {
			childs = dir.listFiles();
			for (final File currentChild : childs) {
				list.addAll(readDirectory(currentChild));
			}
		} else {
			name = dir.getName();
			if (name.matches("\\w\\d{4}.xml")) {
				list.add(dir);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param directory
	 * @return
	 */
	public void index(final String directory, StopWordMap stopWordMap) {
		WordMap wordMap = new WordMap();
		RootIndexMap rootIndexMap = new RootIndexMap();
		final WordsFrequencyMap wordsFrequencyMap = new WordsFrequencyMap();
		int fileNumber = 0;
		int length = 0;
		int maxSizeIndex = getMaxSizeIndex();

		final HashMap<String, String> notWellFormed = new HashMap<String, String>();
		// los siguientes archivos XML parecen no validar con la DTD
		// y dan problemas en el indexado. Por ahora los omitimos.
		
		notWellFormed.put("xml/cg/1995/g1069.xml", null);
		notWellFormed.put("xml/cg/1996/g5071.xml", null);
		notWellFormed.put("xml/cg/1997/g6110.xml", null);
		notWellFormed.put("xml/tk/1995/k0454.xml", null);
		notWellFormed.put("xml/dt/1995/d1024.xml", null);
		notWellFormed.put("xml/pd/1995/p4008.xml", null);
		notWellFormed.put("xml/so/1995/s6088.xml", null);
		notWellFormed.put("xml/tc/1995/t0453.xml", null);
		notWellFormed.put("xml/tc/1995/t0624.xml", null);
		notWellFormed.put("xml/tc/1995/t0957.xml", null);
		notWellFormed.put("xml/tc/1995/t1073.xml", null);
		notWellFormed.put("xml/tc/1996/t0714.xml", null);
		notWellFormed.put("xml/tc/1996/t0892.xml", null);
		notWellFormed.put("xml/tc/1997/t0162.xml", null);
		notWellFormed.put("xml/tc/1998/t0527.xml", null);

		
		

		ArrayList<File> listFiles;
		DocumentReader currentDoc;
		WordCounter wordCounter;
		final WordList wl = new WordList();

		try {
			listFiles = readDirectory(new File(directory));
			System.out.println(listFiles.size() + " archivos.");
			int i = 0;
			for (final File file : listFiles) {

				if (!notWellFormed.containsKey(file.getPath())) {

					// 1. Extraer palabra-frecuencia

					// wordCounter = new WordCounter(file.getPath(),
					// wordsFrequencyMap);
					// wordCounter.readDocument();
					// wordsFrequencyMap.print(150);
					// si la palabra aparece < 500000 veces
					// es decir, imprimir todas las palabras
					// wordsFrequencyMap.printXML("stop-words-ok.xml", 500000,
					// 2);

					// 2. Indexar (habría que pasar un parámetro nuevo -
					// wordsFrequencyMap)

					currentDoc = new DocumentReader(file.getPath(), wordMap,
							stopWordMap, rootIndexMap, fileNumber + 1);
					// además creamos un wordList (aunque no es necesario)
					final WordList wordList = currentDoc.readDocument();
					currentDoc = null;
					System.out.println("[" + i + "] " + file.getPath() + " ["
							+ length / (1024 * 1024) + " MB / "
							+ wordMap.size() + " elements]");
					length += file.length();
					// Guardamos el índice y creamos uno nuevo vacío
					if (length / (1024 * 1024) > maxSizeIndex) {
						length = 0;
						fileNumber++;
						wordMap.printXML(getOutputPath() + "index_" + fileNumber + ".xml");
						wordMap = new WordMap();
					}
				}

				// index.addAll(wordList);
				i++;
			}
			wordMap.printXML( getOutputPath() + "index_" + (fileNumber + 1) + ".xml");

			rootIndexMap.printXML(getOutputPath() + "root_index.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		IndexFactory dr;
		ArrayList<File> listFiles = new ArrayList<File>();

		if (args.length != 1) {
			System.err.println("Use: java DirectoryReader <directory>");
		} else {
			dr = new IndexFactory();
			listFiles = dr.readDirectory(new File(args[0]));

			for (final File file : listFiles) {
				System.out.println("File: " + file.getPath());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	protected String getOutputPath() {
		return outputPath;
	}

	/**
	 * 
	 * @param outputPath
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * @return the maxSizeIndex
	 */
	public final int getMaxSizeIndex() {
		return maxSizeIndex;
	}

	/**
	 * @param maxSizeIndex the maxSizeIndex to set
	 */
	public final void setMaxSizeIndex(int maxSizeIndex) {
		this.maxSizeIndex = maxSizeIndex;
	}

}
