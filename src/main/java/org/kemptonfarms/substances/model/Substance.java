package org.kemptonfarms.substances.model;

import java.lang.String;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(namespace = "org.kemptonfarms.substances.model.Hsdb")
public class Substance {
    private String name;
    private ArrayList<Synonym> synonyms;
    private String molecularFormula;
    private String boilingPoint;
    private String meltingPoint;
    private ArrayList<MajorUse> majorUses;

    @XmlElement(name = "NameOfSubstance")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "sy")
    public ArrayList<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    @XmlElement(name = "mf")
    public String getMolecularFormula() {
        return molecularFormula;
    }

    public void setMolecularFormula(String molecularFormula) {
        this.molecularFormula = molecularFormula;
    }

    @XmlElement(name = "bp")
    public String getBoilingPoint() {
        return boilingPoint;
    }

    public void setBoilingPoint(String boilingPoint) {
        this.boilingPoint = boilingPoint;
    }

    @XmlElement(name = "mp")
    public String getMeltingPoint() {
        return meltingPoint;
    }

    public void setMeltingPoint(String meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    @XmlElement(name = "use")
    public ArrayList<MajorUse> getMajorUses() {
        return majorUses;
    }

    public void setMajorUses(ArrayList<MajorUse> majorUses) {
        this.majorUses = majorUses;
    }
}