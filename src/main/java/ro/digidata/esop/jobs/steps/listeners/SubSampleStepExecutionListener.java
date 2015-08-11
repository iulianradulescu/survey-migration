/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 *
 * @author iulian.radulescu
 */
public class SubSampleStepExecutionListener extends StepExecutionListenerSupport {
    
    public ExitStatus afterStep(StepExecution se ) {
        if ( se.getSkipCount( ) > 0 ) {
            return new ExitStatus("PROBLEMATIC_QUEST_INST_IDS");
        }
        
        return ExitStatus.COMPLETED;
    }
}
