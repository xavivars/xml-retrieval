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

package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author ebenimeli
 * 
 */
public class WordList extends ArrayList<Word> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private int times;

	/**
	 * 
	 * 
	 */
	public final void print() {
		for (final Word w : this) {
			w.printInfo();
		}
	}

	/**
	 * 
	 * @param dos
	 */
	public final void printXML(DataOutputStream dos) {
		try {
			dos.writeBytes("<word>\n");
			dos.writeBytes("<value>" + get(0).getValue() + "</value>\n");

			for (final Word w : this) {
				w.printXML(dos);
			}
			dos.writeBytes("</word>\n");
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
