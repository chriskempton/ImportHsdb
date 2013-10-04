package org.kemptonfarms.substances.model;

import java.lang.String;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlValue;

@Entity
@XmlRootElement(namespace = "org.kemptonfarms.substances.model.Hsdb")
public class Substance {
    @Id
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="molecularformula")
    private String molecularFormula;
    @Column(name="boilingpoint")
    private String boilingPoint;
    @Column(name="meltingpoint")
    private String meltingPoint;
//    @Column(name="synonyms")
    private ArrayList<Synonym> synonyms;
//    @Column(name="majoruses")
    private ArrayList<MajorUse> majorUses;

    @XmlElement(name = "CASRegistryNumber")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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