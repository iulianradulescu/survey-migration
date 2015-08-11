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
import ro.digidata.esop.domain.enums.QuestionnaireStatus;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Questionnaire.findBySampleAndQuest", query = "select q from Questionnaire q where q.sample=?1 and q.questType=?2"),
    @NamedQuery(name = "Questionnaire.findActiveQuestionnaire", query = "select q from Questionnaire q where q.sample=?1 and q.status=1"),
    @NamedQuery(name = "Questionnaire.deleteForSurvey", query = "delete from Questionnaire  q where q.sample in (select id from Sample s where s.survey=:survey)")
})
public class Questionnaire {

    @Id
    @SequenceGenerator(name = "SQQUEST", sequenceName = "SQ_QUESTIONNAIRE", allocationSize = 1)
    @GeneratedValue(generator = "SQQUEST")
    private int id;

    @Column(name = "QUEST_TYPE_ID")
    private String questType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SAMPLE_ID")
    private Sample sample;

    private QuestionnaireStatus status;
    
    @Column(name="QUEST_EXT_KEY")
    private String extendedKey;
    
    @Column(name="TITLE")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestType() {
        return questType;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public QuestionnaireStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionnaireStatus status) {
        this.status = status;
    }

    public Sample getSample() {
        return sample;
    }

    public void setExtendedKey(String extendedKey) {
        this.extendedKey = extendedKey;
    }

    public String getExtendedKey() {
        return extendedKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
