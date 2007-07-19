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

import utils.WordCounter;
import utils.WordsFrequencyMap;

/**
 * 
 * @author ebenimeli
 * 
 */
public class StopWordFactory extends IndexFactory {

	/**
	 * 
	 */
	private int bottomLimit;

	/**
	 * 
	 */
	private int topLimit;

	/**
	 * 
	 * 
	 */
	public StopWordFactory() {
		setBottomLimit(0);
		setTopLimit(Integer.MAX_VALUE);
	}

	/**
	 * 
	 * @param outputPath
	 */
	public StopWordFactory(String outputPath) {
		super(outputPath);
		setBottomLimit(0);
		setTopLimit(Integer.MAX_VALUE);
	}

	/**
	 * 
	 * @param directory
	 * @param stopWordMap
	 */
	public void getStopWords(final String directory) {
		WordsFrequencyMap wordsFrequencyMap = new WordsFrequencyMap();

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
		WordCounter wordCounter;

		try {
			listFiles = readDirectory(new File(directory));
			System.out.println(listFiles.size() + " archivos.");
			int i = 0;
			for (final File file : listFiles) {
				if (!notWellFormed.containsKey(file.getPath())) {
					// 1. Extraer palabra-frecuencia
					wordCounter = new WordCounter(file.getPath(),
							wordsFrequencyMap);
					wordCounter.readDocument();
					// wordsFrequencyMap.print(150);
					// si la palabra aparece < 500000 veces
					// es decir, imprimir todas las palabras
					System.out.println(wordsFrequencyMap.size()
							+ " stop-words.");
					wordsFrequencyMap.printXML(getOutputPath(), getTopLimit(),
							getBottomLimit());
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the bottomLimit
	 */
	public final int getBottomLimit() {
		return bottomLimit;
	}

	/**
	 * @param bottomLimit
	 *            the bottomLimit to set
	 */
	public final void setBottomLimit(int bottomLimit) {
		this.bottomLimit = bottomLimit;
	}

	/**
	 * @return the topLimit
	 */
	public final int getTopLimit() {
		return topLimit;
	}

	/**
	 * @param topLimit
	 *            the topLimit to set
	 */
	public final void setTopLimit(int topLimit) {
		this.topLimit = topLimit;
	}

}
