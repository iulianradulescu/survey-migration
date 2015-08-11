/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.digidata.esop.domain;

import javax.persistence.CascadeType;
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

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name="UNIT_NONRESPONSE")
@NamedQueries({
    @NamedQuery(name="UnitNonResponse.findBySampleAndInstance", query="select u from UnitNonResponse u where u.sample=?1 and u.instance=?2"),
    @NamedQuery(name = "UnitNonResponse.deleteForSurvey", query = "delete from UnitNonResponse  u where u.sample in (select id from Sample s where s.survey=:survey)")
})
public class UnitNonResponse {
    
    @Id
    @SequenceGenerator(name = "SQNONRESP", sequenceName = "SQ_UNIT_NONRESPONSE", allocationSize = 1)
    @GeneratedValue(generator = "SQNONRESP")
    private long id;
    
    @Column(name="NONRESPONSE_CODE")
    private int nonresponse;
    
    @ManyToOne
    @JoinColumn(name="SAMPLE_ID")
    private Sample sample;
   
    //@ManyToOne
    @Column(name="INSTANCE_ID")
    private long instance;

    public int getNonresponse() {
        return nonresponse;
    }

    public void setNonresponse(int nonresponse) {
        this.nonresponse = nonresponse;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public long getInstance() {
        return instance;
    }

    public void setInstance(long instance) {
        this.instance = instance;
    }
    
    public boolean equals( Object object ) {
        if ( object == null || !(object instanceof UnitNonResponse) ) {
            return false;
        }
        
        UnitNonResponse other = ( UnitNonResponse ) object;
        return ( other.getSample().equals( this.sample ) && other.instance == this.instance );
    }
    
    
}
