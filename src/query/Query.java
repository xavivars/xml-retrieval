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

import java.util.ArrayList;
import java.util.Iterator;

import utils.SimpleWord;

/**
 * 
 * @author
 * 
 */
public class Query extends ArrayList<SimpleWord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -660371061917981165L;

	/**
	 * 
	 * 
	 */
	public final void print() {
		for (final SimpleWord w : this) {
			w.printInfo();
		}
	}

        
        public final boolean containsQuery (String word) {
            boolean find = false;
            
            Iterator it = iterator();
            
            while (it.hasNext() && !find) {
                String w = ((SimpleWord) it.next()).getValue();
                if (w.compareTo(word) == 0) {
                    find = true;
                }
            }
            
            return find;
        }
}
