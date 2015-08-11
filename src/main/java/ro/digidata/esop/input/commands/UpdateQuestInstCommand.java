/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.io.File;
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
@Component("update")
public class UpdateQuestInstCommand extends UserCommand {

    @Autowired
    private JobLauncher launcher;

    @Resource(name = "updateQuestInstJob")
    private Job job;

    @Override
    public void execute(String[] parameters) {
        if (parameters == null || parameters.length != 1) {
            throw new InvalidCommandException(String.format("Invalid number of parameters. Expected 1 found %d", parameters == null ? 0 : parameters.length));
        }

        File file = new File(parameters[0]);
        if (!file.exists()) {
            throw new InvalidCommandException("Expected parameter should be a path to oa file on the disk!");
        }

        try {
            JobParameters jobParams = new JobParametersBuilder().addString("inputFile", parameters[0]).toJobParameters();
            launcher.run(job, jobParams);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
