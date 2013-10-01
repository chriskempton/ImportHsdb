package org.kemptonfarms.substances.app;

import org.kemptonfarms.substances.model.*;

import java.util.ArrayList;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Lister {

    public static void main(String[] args) {
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Hsdb.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("data/hsdb.xml");

            // this will create Java object - country from the XML file
            Hsdb hazardousSubstancesDb = (Hsdb) jaxbUnmarshaller.unmarshal(XMLfile);

            ArrayList<Substance> hazardousSubstances = hazardousSubstancesDb.getSubstances();

            int i=0;
            for(Substance substance:hazardousSubstances)
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

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
    }
}