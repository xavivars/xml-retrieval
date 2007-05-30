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


import java.util.Collections;
import query.QueryManager;
import query.Relevance;
import query.WordResultList;

/**
 *
 * @author david
 */
public class Search {

    /** Creates a new instance of Search */
    public Search() {

    }

    public static void main (String [] args) {


        String stopFile, rootFile, queryFile;

        stopFile = "";
        rootFile = "";
        queryFile = "";

        if (args.length == 3) {
            stopFile = args [0];
            rootFile = args [1];
            queryFile = args [2];
            QueryManager qm = new QueryManager (stopFile, rootFile);
            WordResultList wrl=qm.processQuery(queryFile);
            Relevance relevance=qm.transform(wrl);
            relevance.setRelevance();
            System.out.println("Unsorted Relevances");
            System.out.println("----------");
            relevance.print();
            System.out.println("----------");
            System.out.println("End unsorted relevances");
            System.out.println("Sorted Relevances");
            System.out.println("----------");
            Collections.sort(relevance);
            relevance.print();
            System.out.println("----------");
            System.out.println("End relevances");


        }
    }
}
