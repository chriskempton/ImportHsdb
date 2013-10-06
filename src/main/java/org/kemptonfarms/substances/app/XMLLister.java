package org.kemptonfarms.substances.app;

import org.kemptonfarms.substances.model.*;
import org.kemptonfarms.substances.util.HsdbUtil;

import java.util.ArrayList;

public class XMLLister {

    public static void main(String[] args) {
        int i=0;
        for(Substance substance: HsdbUtil.getSubstances())
        {
            i++;
            System.out.println("Substance #"+i);
            System.out.println("Name="+substance.getName());
            System.out.println("Molecular Formula="+substance.getMolecularFormula());
            System.out.println("Melting Point="+substance.getMeltingPoint());
            System.out.println("Boiling Point="+substance.getBoilingPoint());

            System.out.println("Synonyms...");

            int synCount = 1;
            ArrayList<Synonym> synonyms = substance.getSynonyms();
            for(Synonym synonym:synonyms)
            {
                System.out.println(synCount+". "+synonym.getValue());
                synCount++;
            }

            System.out.println("Major uses...");

            int useCount = 1;
            ArrayList<MajorUse> uses = substance.getMajorUses();
            for(MajorUse use:uses)
            {
                System.out.println(useCount+". "+use.getValue());
                useCount++;
            }
        }
    }
}