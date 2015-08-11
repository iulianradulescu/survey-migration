/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.digidata.esop.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Instance.findBySurvey", query="select i.id from Instance i where i.survey.id=:survey and i.survey.status=13 order by i.id desc"),
    @NamedQuery(name="Instance.findActiveBySurvey", query="select i.id from Instance i where i.survey.id=:survey and i.survey.status=13 and i.period !=0 order by i.id desc"),
    @NamedQuery(name="Instance.findHistoricalInstanceBySurvey", query="select i.id from Instance i where i.survey.id=:survey and i.survey.status=13 and i.period=0")
})
public class Instance {
    
    @Id
    private long id;
    
    @ManyToOne
    @JoinColumn(name="SURVEY_ID")
    private Survey survey;
    
    @Column(name="START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name="END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    private int period;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    
    public boolean isStillCollecting( ) {
	return startDate.before( new Date( )  )  &&  endDate.after( new Date( ) );
    }
}
