/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
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
import ro.digidata.esop.jobs.steps.listeners.SubSampleStepExecutionListener;

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
                .start(stepConfiguration.smStatisticalUnitSurveyUser())
                .split(jobExecutor())
                .add(flow(stepConfiguration.smSample(), stepConfiguration.smMicrodata()))
                .on("COMPLETED")
                .to(stepConfiguration.smValidation()).end().build();
    }

    @Bean
    public Job unicaMigrationJob() {
        return jobs.get("unicaMigrationJob")
                .start(stepConfiguration.smStatisticalUnitSurveyUser())
                .split(jobExecutor()).add(flow(stepConfiguration.smSample(), stepConfiguration.unicaMicrodata()))
                .on("COMPLETED")
                .to(stepConfiguration.smValidation()).end().build();
    }

    @Bean
    public Job s3MigrationJob() {
        return jobs.get("s3MigrationJob")
                .start(stepConfiguration.smStatisticalUnitSurveyUser())
                .split(jobExecutor()).add(flow(stepConfiguration.smSample(), stepConfiguration.s3Microdata()))
                .on("COMPLETED")
                .to(stepConfiguration.s3Validation()).end().build();
    }

    @Bean
    public Job subSampleMigrationJob() {
        return jobs.get("subSampleMigrationJob").start(stepConfiguration.smSubSample()).on("COMPLETED")
                .to(stepConfiguration.smSubSampleMicrodata())
                .next(stepConfiguration.smSubSampleStatisticalUnitSurveyUser())
                .end()
                .build();
    }

    private Flow flow(Step sampleStep, Step microdataStep) {
        return new FlowBuilder<Flow>("flow").from(sampleStep).next(microdataStep).end();
    }
    
    @Bean(destroyMethod = "")
    protected TaskExecutor jobExecutor() {
        TaskExecutor executor = new SimpleAsyncTaskExecutor();
        return executor;
    }
}
