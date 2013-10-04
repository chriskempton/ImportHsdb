package org.kemptonfarms.substances.model;

import java.lang.String;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Entity
@XmlRootElement(namespace = "org.kemptonfarms.substances.model.Substance")
public class MajorUse {
    private String value;

    @Column(name="maj")
    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}