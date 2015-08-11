/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.tasklets;

import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.repositories.ReportingUnitUserRepository;
import ro.digidata.esop.repositories.SampleRepository;

/**
 *
 * @author radulescu
 */
public class RevertSurveyMigration implements Tasklet {

     protected Logger logger = LoggerFactory.getLogger(RevertSurveyMigration.class);
    
    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private ReportingUnitUserRepository ruuRepository;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        Long survey = (Long) cc.getStepContext().getJobParameters().get("survey");
                
        List<ReportingUnitUser> users = ruuRepository.findBySurvey(survey);
        logger.info("Number of users = " + users.size());

        //delete from sample on cascade
        
        Long rows = sampleRepository.deleteBySurvey(survey);
       logger.info("SAMPLE - NUMBER OF ROWS DELETED = " + rows);

        return RepeatStatus.FINISHED;
    }

}
