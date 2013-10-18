package org.kemptonfarms.substances.app;

import org.kemptonfarms.substances.model.*;
import org.kemptonfarms.substances.util.IHsdbDataUtil;
import org.kemptonfarms.substances.util.XmlHsdbDataUtil;

import java.util.List;

public class DataViewer {

    public static void main(String[] args) {
        int i=0;
        IHsdbDataUtil inputDataUtil = new XmlHsdbDataUtil();

        for(Substance substance: inputDataUtil.getSubstances())
        {
            i++;
            System.out.println("Substance #"+i);
            System.out.println("Name="+substance.getName());
            System.out.println("Molecular Formula="+substance.getMolecularFormula());
            System.out.println("Melting Point="+substance.getMeltingPoint());
            System.out.println("Boiling Point="+substance.getBoilingPoint());

            System.out.println("Synonyms...");

            int synCount = 1;
            List<String> synonyms = substance.getSynonyms();
            for(String synonym:synonyms)
            {
                System.out.println(synCount+". "+synonym);
                synCount++;
            }

            System.out.println("Major uses...");

            int useCount = 1;
            List<String> uses = substance.getMajorUses();
            for(String use:uses)
            {
                System.out.println(useCount+". "+use);
                useCount++;
            }
        }
    }
}