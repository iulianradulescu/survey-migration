/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.repositories.ReportingUnitUserRepository;
import ro.digidata.esop.repositories.TMicrodataRepository;

/**
 *
 * @author radulescu
 */

public class RevertSurveyMigration implements Tasklet {
    
    @Autowired
    private TMicrodataRepository tmRepository;
    
    @Autowired
    private ReportingUnitUserRepository ruuRepository;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
	Long survey = (Long)cc.getStepContext().getJobParameters().get("survey");
	
	System.out.println("SURVEY == " + survey );
	List<ReportingUnitUser> users = ruuRepository.findBySurvey( survey);
	System.out.println("Number of users = " + users.size());
	
	Long rows = tmRepository.deleteByInstanceSurveyId( survey );
	System.out.println("NUMBER OF ROWS = " + rows );
	
	return RepeatStatus.FINISHED;
    }
    
}
