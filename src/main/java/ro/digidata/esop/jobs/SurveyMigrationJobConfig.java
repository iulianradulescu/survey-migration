/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import ro.digidata.esop.jobs.steps.SurveyMigrationStepConfig;

/**
 *
 * @author radulescu
 */
@Configuration
public class SurveyMigrationJobConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private SurveyMigrationStepConfig stepConfiguration;

    @Autowired
    private EntityManagerFactory emf;

    @Bean
    public Job simpleMigrationJob() {
        return jobs.get("simpleMigrationJob")
                .start(stepConfiguration.smStatisticalUnitSurveyUser( ) )
                .split(jobExecutor( )).add(flow( ) ).on("COMPLETED")
                .to(stepConfiguration.smValidation( ) ).end().build();

        //return jobs.get("surveyMigrationJob").start(step0()).next(step1()).next(step2()).build();
    }

    @Bean(name = "smFlow")
    public Flow flow() {
        return new FlowBuilder<Flow>("flow").from(stepConfiguration.smSample()).next(stepConfiguration.smMicrodata()).end();
    }

    @Bean
    protected TaskExecutor jobExecutor() {
        TaskExecutor executor = new SimpleAsyncTaskExecutor();
        return executor;
    }

}
