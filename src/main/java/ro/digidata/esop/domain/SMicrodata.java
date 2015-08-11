/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name = "OLD_MICRODATA")
public class SMicrodata {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    @Basic(fetch = FetchType.LAZY)
    private SSample sample;

    @Column(name = "QUEST_INST_ID")
    private String questInst;

    @Column(name = "QUEST_INST_INFO")
    private String questInstInfo;

    @Column(name = "QUEST_TYPE_ID")
    private String questType;

    @ManyToOne
    @JoinColumn(name = "INSTANCE_ID")
    private Instance instance;

    private Integer nonresponse;

    private String respondent;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String interview;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String correlations;

    private Integer correlation;

    @Column(name = "LAST_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "LAST_COMPUTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastComputed;

    private long version;

    @Column(name = "FINAL_SAVE")
    private char finalSave;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SSample getSample() {
        return sample;
    }

    public void setSample(SSample sample) {
        this.sample = sample;
    }

    public String getQuestInst() {
        return questInst;
    }

    public void setQuestInst(String questInst) {
        this.questInst = questInst;
    }

    public String getQuestType() {
        return questType;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public Integer getNonresponse() {
        return nonresponse;
    }

    public void setNonresponse(Integer nonresponse) {
        this.nonresponse = nonresponse;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getInterview() {
        return interview;
    }

    public void setInterview(String interview) {
        this.interview = interview;
    }

    public String getCorrelations() {
        return correlations;
    }

    public Integer getCorrelation() {
        return correlation;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getLastComputed() {
        return lastComputed;
    }

    public long getVersion() {
        return version;
    }

    public char getFinalSave() {
        return finalSave;
    }

    public String getQuestInstInfo() {
        return questInstInfo;
    }

    
}
