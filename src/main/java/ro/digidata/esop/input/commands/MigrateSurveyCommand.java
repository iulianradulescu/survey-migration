/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.digidata.esop.jobs.SurveyMigrationJobClassifier;
import ro.digidata.esop.services.SurveyService;
import ro.digidata.esop.services.model.SurveyInfo;

/**
 *
 * @author radulescu
 */
@Component("migrate")
public class MigrateSurveyCommand extends SurveyCommand {

    @Autowired
    private JobLauncher launcher;

    @Autowired
    private SurveyMigrationJobClassifier jobClassifier;
    
    @Autowired
    private SurveyService surveyService;

    @Override
    protected void doExecute(Long survey) {
       try {
           SurveyInfo sInfo = surveyService.surveyInfo( survey );
           
            Job job = jobClassifier.classify( sInfo.sClass( ) );
            
            JobParameters jobParams = new JobParametersBuilder().addLong("survey", survey ).toJobParameters();
            JobExecution exec = launcher.run(job, jobParams);
            logger.info("Migration finalized with status " + exec.getExitStatus().getExitCode() + " time = " + (exec.getEndTime().getTime() - exec.getStartTime().getTime())/1000f + " sec");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
