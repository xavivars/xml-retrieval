package utils;

import java.io.DataOutputStream;

/*
 * Copyright (C) 2007 
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

/**
 * 
 */
public class Word extends SimpleWord {

	/**
	 * 
	 */
	private String document;

	/**
	 * 
	 */
	private String path;

	/**
	 * 
	 * @param value
	 */
	public Word(final String value) {
		super(value);
	}

	/**
	 * @return the document
	 */
	public final String getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public final void setDocument(final String document) {
		this.document = document;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public final void setPath(final String path) {
		this.path = path;
	}

	/**
	 * 
	 * 
	 */
	@Override
	public void printInfo() {
		System.out.println("Word:      '" + getValue() + "'");
		System.out.println("Documento: '" + getDocument() + "'");
		System.out.println("Path:      '" + getPath() + "'");
	}

	/**
	 * 
	 * @param dos
	 */
	public final void printXML(DataOutputStream dos) {
		try {
			dos.writeBytes("\t<loc>\n");
			dos.writeBytes("\t\t<doc>" + getDocument() + "</doc>\n");
			dos.writeBytes("\t\t<ref>" + getPath() + "</ref>\n");
			dos.writeBytes("\t</loc>\n");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
