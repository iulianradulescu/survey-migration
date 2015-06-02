/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name = "STATISTICAL_UNIT_DATA")
@NamedQueries({
    @NamedQuery(name = "StatisticalUnitData.findBySUandAttribute", query = "select sud from StatisticalUnitData sud where sud.statisticalUnit.id=:statisticalUnit and sud.attribute.id=:attribute and sud.historical=0"),
     @NamedQuery(name = "StatisticalUnitData.findBySU", query = "select sud from StatisticalUnitData sud where sud.statisticalUnit.id=:statisticalUnit and sud.historical=0"),
    @NamedQuery(name = "StatisticalUnitData.removeForPopulation", query = "delete from StatisticalUnitData sud where sud.statisticalUnit in (select su from StatisticalUnit su where su.population=:population)")
})
public class StatisticalUnitData {

    @Id
    @SequenceGenerator(name = "SQSUD", sequenceName = "SQ_STATISTICAL_UNIT_DATA", allocationSize = 1)
    @GeneratedValue(generator = "SQSUD")
    private long id;

    @ManyToOne
    @JoinColumn(name = "STATISTICAL_UNIT_ID")
    private StatisticalUnit statisticalUnit;

    @ManyToOne
    @JoinColumn(name = "POPULATION_STRUCTURE_ID")
    private PopulationStructure attribute;

    private String value;

    @Column(name = "HISTORICAL", columnDefinition = "INT(1)")
    private boolean historical = false;
    
    @Column(name="MODIFICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate = new Date( );

    public long getId( ) {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StatisticalUnit getStatisticalUnit() {
        return statisticalUnit;
    }

    public void setStatisticalUnit(StatisticalUnit statisticalUnit) {
        this.statisticalUnit = statisticalUnit;
    }

    public PopulationStructure getAttribute() {
        return attribute;
    }

    public void setAttribute(PopulationStructure attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
