/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.repositories.SSampleRepository;
import ro.digidata.esop.repositories.SampleRepository;

/**
 *
 * @author radulescu
 */
public class ValidateSurveyMigration implements Tasklet {

    @Autowired
    private SSampleRepository ssampleRepository;
    
    @Autowired
    private SampleRepository sampleRepository;
    
    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
	Long survey = (Long)cc.getStepContext().getJobParameters().get("survey");
	
	//validate the records from SAMPLE, QUESTIONNAIRE. MICRODATA
	Long originalCount = ssampleRepository.countBySurveyId( survey );
	Long newCount = sampleRepository.countBySurvey( survey );
	
	System.out.println("SAMPLE RECORDS = [ ORIGINAL = " + originalCount + " | NEW = " + newCount + "]");
	
	return RepeatStatus.FINISHED;
    }
    
}
