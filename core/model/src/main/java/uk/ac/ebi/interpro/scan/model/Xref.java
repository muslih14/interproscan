/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.interpro.scan.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Database cross-reference.
 *
 * @author  Antony Quinn
 * @author  Phil Jones
 * @version $Id$
 */
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
@XmlType(name="XrefType")
abstract class Xref implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="XREF_IDGEN")
    @TableGenerator(name="XREF_IDGEN", table="KEYGEN", pkColumnValue="xref", initialValue = 0, allocationSize = 50)
    protected Long id;

    @Column (name = "ac", nullable = false, unique = false, updatable = false)
    @Index (name="xref_accession_idx")
    private String accession;

    @Column (name = "name", nullable = false, unique = false, updatable = false)
    private String name;

    @Column (name = "db", nullable = false, unique = false, updatable = false)
    private String databaseName;

    /**
     * Zero arguments constructor for Hibernate.
     */
    protected Xref() { }

    public Xref(String accession){
        this.accession = accession;
    }

    public Xref(String databaseName, String accession, String name) {
        this.databaseName = databaseName;
        this.accession    = accession;
        this.name         = name;
    }


    @XmlTransient
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute(name="ac", required=true)
    public String getAccession() {
        return accession;
    }

    private void setAccession(String accession) {
        this.accession = accession;
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="db")
    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Xref))
            return false;
        final Xref x = (Xref) o;
        return new EqualsBuilder()
                .append(accession, x.accession)
                .append(name, x.name)
                .append(databaseName, x.databaseName)
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(15, 37)
                .append(accession)
                .append(name)
                .append(databaseName)
                .toHashCode();
    }

    @Override public String toString()  {
        return ToStringBuilder.reflectionToString(this);
    }

}