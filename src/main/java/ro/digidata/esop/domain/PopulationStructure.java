/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import ro.digidata.esop.domain.enums.YesNo;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name = "POPULATION_STRUCTURE")
@NamedQueries({
    @NamedQuery(name = "PopulationStructure.findByPopulationAndCode", query = "select ps from PopulationStructure ps where ps.population.id=:population and ps.mnemonic=:mnemonic")
})
public class PopulationStructure {

    @Id
    @SequenceGenerator(name = "SQPOPSTRUCT", sequenceName = "SQ_POPULATION_STRUCTURE", allocationSize = 1)
    @GeneratedValue(generator = "SQPOPSTRUCT")
    private int id;

    @ManyToOne
    @JoinColumn(name="POPULATION_ID")
    private Population population;

    private String mnemonic;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Lob
    @Column(name = "TYPE_DEFINITION")
    private String typeDefinition;

    private YesNo mandatory;
    private YesNo editable;
    private YesNo searchable;
    private YesNo printable;
    private YesNo exportable;

    @Column(name = "KEEP_HISTORY")
    private YesNo keepHistory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setTypeDefinition(String typeDefinition) {
        this.typeDefinition = typeDefinition;
    }

    public void setMandatory(YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public void setEditable(YesNo editable) {
        this.editable = editable;
    }

    public void setSearchable(YesNo searchable) {
        this.searchable = searchable;
    }

    public void setPrintable(YesNo printable) {
        this.printable = printable;
    }

    public void setExportable(YesNo exportable) {
        this.exportable = exportable;
    }

    public void setKeepHistory(YesNo keepHistory) {
        this.keepHistory = keepHistory;
    }

    public String getMnemonic() {
        return mnemonic;
    }
    
    
}
