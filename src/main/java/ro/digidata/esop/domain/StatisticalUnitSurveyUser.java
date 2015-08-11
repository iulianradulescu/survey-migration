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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name="STATISTICAL_UNIT_SURVEY_USER")
public class StatisticalUnitSurveyUser {
    
    @Id
     @SequenceGenerator(name = "SQSUSU", sequenceName = "SQ_STATISTICAL_UNIT_SURVEY_USR", allocationSize = 1)
    @GeneratedValue(generator = "SQSUSU")
    private int id;
    
    @Column(name="STATISTICAL_UNIT_ID")
    private long unit;
    
    private  String username;
    
    @Column(name="SURVEY_ID")
    private long survey;

    public void setUnit(long unit) {
        this.unit = unit;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSurvey(long survey) {
        this.survey = survey;
    }
    
    
}
