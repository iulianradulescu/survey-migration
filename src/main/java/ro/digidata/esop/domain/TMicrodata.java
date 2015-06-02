/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import java.util.Date;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * target microdata entity
 * @author iulian.radulescu
 */
@Entity
@Table(name="MICRODATA")
@NamedQueries({
    @NamedQuery(name="Microdata.deleteForSurvey", query="delete from TMicrodata m where m.instance.id in (select id from Instance i where i.survey.id=:survey)")
})
public class TMicrodata {

    @Id
    @SequenceGenerator(name = "SQMICRODATA", sequenceName = "SQ_MICRODATA", allocationSize = 1)
    @GeneratedValue(generator = "SQMICRODATA")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="QUESTIONNAIRE_ID")
    private Questionnaire questionnaire;

    @ManyToOne
    @JoinColumn(name = "INSTANCE_ID")
    private Instance instance;

    @Lob
    private String interview;

    @Lob
    @Column(name = "CORRELATION_RESULTS")
    private String correlationResults;

    @Column(name = "CORRELATION_STATUS")
    private Integer correlationStatus;

    @Column(name = "LAST_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "LAST_COMPUTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastComputed;

    private long version;
    
    @Column(name="FILL_MODE")
    private String fillMode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public String getInterview() {
        return interview;
    }

    public void setInterview(String interview) {
        this.interview = interview;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public void setCorrelationResults(String correlationResults) {
        this.correlationResults = correlationResults;
    }

    public void setCorrelationStatus(Integer correlationStatus) {
        this.correlationStatus = correlationStatus;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setLastComputed(Date lastComputed) {
        this.lastComputed = lastComputed;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setFillMode(String fillMode) {
        this.fillMode = fillMode;
    }
    
    
}
