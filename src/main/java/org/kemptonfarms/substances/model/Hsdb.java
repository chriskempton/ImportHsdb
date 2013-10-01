package org.kemptonfarms.substances.model;

import java.lang.String;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Hsdb {
    private ArrayList<Substance> substances;

    @XmlElement(name = "DOC")
    public ArrayList<Substance> getSubstances() {
        return substances;
    }

    public void setSubstances(ArrayList<Substance> substances) {
        this.substances = substances;
    }
}