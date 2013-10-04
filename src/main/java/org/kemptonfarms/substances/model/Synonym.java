package org.kemptonfarms.substances.model;

import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Entity
@XmlRootElement(namespace = "org.kemptonfarms.substances.model.Substance")
public class Synonym {
    @Column(name="syn")
    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}