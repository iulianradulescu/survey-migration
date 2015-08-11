/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.model;

/**
 * identifies a subsample record in the current implementation in order to transform it (or not) into a statistical_unit
 * @author iulian.radulescu
 */
public class SubSample {
    
    private long sample;
    
    private String questInstId;
    
    private String questInstInfo;
    
    public SubSample( long sample, String questInstId, String questInstInfo ) {
        this.sample = sample;
        this. questInstId = questInstId;
        this.questInstInfo = questInstInfo;
    }

    public long getSample() {
        return sample;
    }

    public String getQuestInstId() {
        return questInstId;
    }

    public String getQuestInstInfo() {
        return questInstInfo;
    }
    
    
}
