/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.digidata.esop.jobs.steps.tasklets.RevertSurveyMigration;

/**
 *
 * @author radulescu
 */
@Configuration
@EnableBatchProcessing
public class RevertMigrationJobConfig {
    
    @Autowired
    private JobBuilderFactory jobs;
    
    
    @Autowired
    private StepBuilderFactory steps;
    
    @Bean(name="revertMigrationJob")
    public Job job( ) {
	return jobs.get("revertMigrationJob").start( revertMigrationStep ( ) ).build();
    }
    
    
    @Bean
    public Step revertMigrationStep( ) {
	return steps.get("steps").tasklet( revertMigrationTasklet( ) ).build();
    }
        
    @Bean
    public Tasklet revertMigrationTasklet( ) {
	return new RevertSurveyMigration();
    }
}
