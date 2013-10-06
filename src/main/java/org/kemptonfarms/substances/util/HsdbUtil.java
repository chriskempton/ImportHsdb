package org.kemptonfarms.substances.util;


import org.kemptonfarms.substances.model.Hsdb;
import org.kemptonfarms.substances.model.Substance;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class HsdbUtil {
    public static ArrayList<Substance> getSubstances() {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Hsdb.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("data/hsdb.xml");

            // this will create Java object - country from the XML file
            Hsdb hazardousSubstancesDb = (Hsdb) jaxbUnmarshaller.unmarshal(XMLfile);

            return hazardousSubstancesDb.getSubstances();
        } catch (JAXBException e) {
            System.out.println("Error creating Substance objects from XML");
            e.printStackTrace();
        }
        return null;
    }
}
