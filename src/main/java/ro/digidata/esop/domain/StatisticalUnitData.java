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
public class StatisticalUnitData implements Comparable {

    @Id
    @SequenceGenerator(name = "SQSUD", sequenceName = "SQ_STATISTICAL_UNIT_DATA", allocationSize = 1)
    @GeneratedValue(generator = "SQSUD")
    private long id;

    @ManyToOne
    @JoinColumn(name = "STATISTICAL_UNIT_ID")
    private StatisticalUnit statisticalUnit;

    @Column(name = "POPULATION_STRUCTURE_ID")
    private long attribute;

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

    public void setStatisticalUnit(StatisticalUnit statisticalUnit) {
        this.statisticalUnit = statisticalUnit;
    }

    public void setAttribute(long attribute) {
        this.attribute = attribute;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    
     public boolean equals( Object object ) {
        if ( object == null || !(object instanceof StatisticalUnitData) ) {
            return false;
        }
        
        StatisticalUnitData other = ( StatisticalUnitData ) object;
        return ( other.statisticalUnit.equals( this.statisticalUnit ) && other.attribute == this.attribute );
    }

    @Override
    public int compareTo(Object o) {
        return 1;
    }
    
}
