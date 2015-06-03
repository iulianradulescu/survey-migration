/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name = "MAXIMAL_LIST_SURVEY_USER")
public class MaximalListSurveyUser {

    @Id
    private Long id;

    @Column(name = "MAXIMAL_LIST_ID")
    private Long unit;

    private String username;

    @Column(name = "SURVEY_ID")
    private Long survey;

    public String getUsername( ) {
        return username;
    }
    
    public Long getUnit( ) {
        return this.unit;
    }
    
    public Long getSurvey( ) {
        return this.survey;
    }
}
