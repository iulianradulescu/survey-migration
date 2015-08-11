/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.digidata.esop.input.exceptions.InvalidCommandException;

/**
 *
 * @author radulescu
 */
@Component("revert")
public class RevertMigrationCommand extends SurveyCommand {

    @Autowired
    private JobLauncher launcher;

    @Resource(name = "revertMigrationJob")
    private Job job;

    @Override
    protected void doExecute(Long survey) {
        try {
            JobParameters jobParams = new JobParametersBuilder().addLong("survey", survey).toJobParameters();
            launcher.run(job, jobParams);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
