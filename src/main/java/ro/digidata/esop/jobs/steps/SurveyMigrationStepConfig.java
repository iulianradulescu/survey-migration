/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import ro.digidata.esop.domain.MaximalListSurveyUser;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.SSample;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.StatisticalUnitSurveyUser;
import ro.digidata.esop.jobs.steps.listeners.SubSampleStepExecutionListener;
import ro.digidata.esop.jobs.steps.model.MicrodataMigrationProcessorOutput;
import ro.digidata.esop.jobs.steps.model.SubSample;
import ro.digidata.esop.jobs.steps.model.SubSampleSurveyUser;
import ro.digidata.esop.jobs.steps.processors.SurveyMigrationProcessorConfig;
import ro.digidata.esop.jobs.steps.readers.SurveyMigrationReaderConfig;
import ro.digidata.esop.jobs.steps.tasklets.TaskletConfig;
import ro.digidata.esop.jobs.steps.writers.SurveyMigrationWriterConfig;

/**
 * configuration class to hold the bean definition for steps
 *
 * @author iulian.radulescu
 */
@Configuration
public class SurveyMigrationStepConfig {

      protected Logger logger = LoggerFactory.getLogger(SurveyMigrationStepConfig.class);
    
    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private SurveyMigrationReaderConfig readerConfiguration;

    @Autowired
    private SurveyMigrationProcessorConfig processorConfiguration;

    @Autowired
    private SurveyMigrationWriterConfig writerConfiguration;

    @Autowired
    private TaskletConfig taskletConfiguration;

    @Bean(destroyMethod = "")
    public Step smSample() {
        return steps.get("smSample").<SSample, Sample>chunk(100).reader(readerConfiguration.sampleReader(null))
                .processor(processorConfiguration.sampleProcessor(null))
                .writer(writerConfiguration.sampleWriter())
                .taskExecutor(stepExecutor())
                .throttleLimit(10)
                .build();
    }

    @Bean(destroyMethod = "")
    public Step smSubSample() {
        StepBuilderHelper builder =  steps.get("smSubSample").<SubSample, Sample>chunk(1).reader(readerConfiguration.subSampleReader(null))
                .processor(processorConfiguration.subSampleProcessor(null))
                .writer(writerConfiguration.sampleWriter())
                .listener( subSampleStepExecutionListener( ) );
               
        return ((SimpleStepBuilder)builder).build();
    }

    @Bean(destroyMethod = "")
    public Step smStatisticalUnitSurveyUser() {
        return steps.get("smStatisticalUnitSurveyUser").<MaximalListSurveyUser, StatisticalUnitSurveyUser>chunk(100)
                .reader(readerConfiguration.maximalListSurveyUserReader(null))
                .processor(processorConfiguration.statisticalUnitSurveyUserProcessor())
                .writer(writerConfiguration.statisticalUnitSurveyUserWriter())
                .build();
    }

    @Bean(destroyMethod = "")
    public Step smSubSampleStatisticalUnitSurveyUser() {
        return steps.get("smSubSampleStatisticalUnitSurveyUser").<SubSampleSurveyUser, StatisticalUnitSurveyUser>chunk(100)
                .reader(readerConfiguration.subSampleSurveyUserReader(null))
                .processor(processorConfiguration.subSampleStatisticalUnitSurveyUserProcessor(null))
                .writer(writerConfiguration.statisticalUnitSurveyUserWriter())
                .build();
    }

    @Bean(destroyMethod = "")
    public Step smMicrodata() {
        return steps.get("smMicrodata").<SMicrodata, MicrodataMigrationProcessorOutput>chunk(10)
                .reader(readerConfiguration.microdataReader(null))
                .processor(processorConfiguration.microdataProcessor(null))
                .writer(writerConfiguration.microdataProcessorOutputWriter())
                .taskExecutor(stepExecutor())
                .throttleLimit(10)
                .build();
    }

    @Bean(destroyMethod = "")
    public Step smSubSampleMicrodata() {
        return steps.get("smSubSampleMicrodata").<SMicrodata, MicrodataMigrationProcessorOutput>chunk(10)
                .reader(readerConfiguration.microdataReader(null))
                .processor(processorConfiguration.subSampleMicrodataProcessor(null))
                .writer(writerConfiguration.microdataProcessorOutputWriter())
                //.taskExecutor(stepExecutor( ) )
                //.throttleLimit(10)
                .build();
    }

    @Bean(destroyMethod = "")
    public Step unicaMicrodata() {
        return steps.get("unicaMicrodata").<SMicrodata, MicrodataMigrationProcessorOutput>chunk(10)
                .reader(readerConfiguration.microdataReader(null))
                .processor(processorConfiguration.unicaMicrodataProcessor(null))
                .writer(writerConfiguration.microdataProcessorOutputWriter())
                .taskExecutor(stepExecutor())
                .throttleLimit(10)
                .build();
    }

    @Bean(destroyMethod = "")
    public Step s3Microdata() {
        return steps.get("s3Microdata").<SMicrodata, MicrodataMigrationProcessorOutput>chunk(10)
                .reader(readerConfiguration.microdataS3Reader(null))
                .processor(processorConfiguration.s3MicrodataProcessor(null))
                .writer(writerConfiguration.microdataProcessorOutputWriter())
                .taskExecutor(stepExecutor())
                .throttleLimit(10)
                .build();
    }

    @Bean(destroyMethod = "")
    public Step smValidation() {
        return steps.get("smValidation").tasklet(taskletConfiguration.validateMigrationTasklet()).build();
    }

    @Bean(destroyMethod = "")
    public Step s3Validation() {
        return steps.get("s3Validation").tasklet(taskletConfiguration.validateS3MigrationTasklet()).build();
    }
    
    @Bean(destroyMethod = "")
    protected StepExecutionListener subSampleStepExecutionListener( ) {
        return new SubSampleStepExecutionListener( );
    }

    @Bean
    protected TaskExecutor stepExecutor() {
        TaskExecutor executor = new SimpleAsyncTaskExecutor();
        return executor;
    }
}
