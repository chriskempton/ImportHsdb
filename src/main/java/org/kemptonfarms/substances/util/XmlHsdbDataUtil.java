package org.kemptonfarms.substances.util;


import org.kemptonfarms.substances.model.Hsdb;
import org.kemptonfarms.substances.model.Substance;

import javax.xml.bind.*;
import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class XmlHsdbDataUtil implements IHsdbDataUtil {
    private final static String xmlFile = "data/hsdb.xml";

    public List<Substance> getSubstances() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Hsdb.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File(xmlFile);

            // this will create Java object - country from the XML file
            Hsdb hazardousSubstancesDb = (Hsdb) jaxbUnmarshaller.unmarshal(XMLfile);

            return hazardousSubstancesDb.getSubstances();
        } catch (JAXBException e) {
            System.out.println("Error creating Substance objects from XML");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Substance getSubstance(String key) {
        try {
            List<Substance> substances = getSubstances();
            for(Substance substance:substances) {
                if(substance.getId().equals(key)) {
                    return substance;
                }
            }
        } catch (Exception e) {
            System.out.println("Error creating a Substance object from XML lookup");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void putSubstances(List<Substance> substances) throws Exception {
        throw new Exception("Substances can't be put into XML");
    }

    @Override
    public void putSubstance(Substance substance) throws Exception {
        throw new Exception("Substances can't be put into XML");
    }
}
