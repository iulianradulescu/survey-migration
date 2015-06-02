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
@Component("migrate")
public class MigrateSurveyCommand extends UserCommand {

   
    @Autowired
    private JobLauncher launcher;

    @Resource(name = "surveyMigrationJob")
    private Job job;

    @Override
    protected Map<String, Object> validate(String[] parameters) {
	Map<String, Object> paramObjects = new HashMap<>();
	
	Long survey;
	if (parameters == null || parameters.length != 1) {
	    throw new InvalidCommandException(String.format("Invalid number of parameters. Expected 1 found %d", parameters == null ? 0 : parameters.length));
	}

	try {
	    survey = Long.parseLong(parameters[0]);
	    if (survey <= 0) {
		throw new InvalidCommandException("Expected parameter should be a positive number!");
	    }
	} catch (NumberFormatException exNFE) {
	    throw new InvalidCommandException("Expected parameter should be a number!");
	}

	paramObjects.put("survey", survey);
	return paramObjects;
    }

    @Override
    protected void execute(Map<String, Object> parametersMap) {
	System.out.println("Start execution");
	try {
	    JobParameters jobParams = new JobParametersBuilder().addLong("survey", (Long) parametersMap.get("survey")).toJobParameters();
	    launcher.run(job, jobParams);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
}
