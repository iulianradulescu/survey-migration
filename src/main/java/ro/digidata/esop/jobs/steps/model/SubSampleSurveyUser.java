/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.model;

/**
 *
 * @author iulian.radulescu
 */
public class SubSampleSurveyUser {
    
    private long maximalListId;
    private String questInstId;
    private String username;
    
    public SubSampleSurveyUser( Long maxmalListId, String questInstId, String username ) {
        this .maximalListId = maxmalListId;
        this.questInstId = questInstId;
        this.username = username;
    }

    public long getMaximalListId() {
        return maximalListId;
    }
    public String getQuestInstId() {
        return questInstId;
    }
    public String getUsername() {
        return username;
    }
}
