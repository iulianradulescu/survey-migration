/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.SSample;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.TMicrodata;
import ro.digidata.esop.jobs.steps.MicrodataMigrationProcessor;
import ro.digidata.esop.jobs.steps.SampleMigrationProcessor;
import ro.digidata.esop.jobs.steps.ValidateSurveyMigration;

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
    private EntityManagerFactory emf;

    @Bean(name = "surveyMigrationJob")
    public Job job() {
	return jobs.get("surveyMigrationJob").start(step0())
		.next(step1())
		.next(step2()).build();
    }

    @Bean(name = "smStep0")
    public Step step0() {
	return steps.get("smStep0").<SSample, Sample>chunk(100)
		.reader(reader0(null))
		.processor(processor0(null))
		.writer(writer0())
		.taskExecutor(executor())
		.throttleLimit(10)
		.build();
    }

    @Bean(name = "smStep1")
    public Step step1() {
	return steps.get("smStep1").<SMicrodata, TMicrodata>chunk(10)
		.reader(reader1(null))
		.processor(processor1(null))
		.writer(writer1())
		.taskExecutor(executor())
		.throttleLimit(10)
		.build();
    }

    @Bean(name = "smValidate")
    public Step step2() {
	return steps.get("smValidate").tasklet(validateMigrationTasklet()).build();
    }

    @Bean(name = "smReader0")
    @StepScope
    public ItemStreamReader<SSample> reader0(@Value("#{jobParameters['survey']}") Long survey) {
	System.out.println(emf == null);
	JpaPagingItemReader<SSample> reader = new JpaPagingItemReader<>();
	reader.setEntityManagerFactory(emf);

	reader.setQueryString("select s from SSample s where s.survey=" + survey);

	reader.setPageSize(1000);

	return reader;
    }

    @Bean(name = "smProcessor0")
    @StepScope
    public ItemProcessor<SSample, Sample> processor0(@Value("#{jobParameters['survey']}") Long survey) {
	return new SampleMigrationProcessor(survey);
    }

    @Bean(name = "smWriter0")
    @StepScope
    public ItemWriter<Sample> writer0() {
	JpaItemWriter<Sample> writer = new JpaItemWriter();
	writer.setEntityManagerFactory(emf);

	return writer;
    }

    @Bean(name = "smReader1")
    @StepScope
    public ItemStreamReader<SMicrodata> reader1(@Value("#{jobParameters['survey']}") Long survey) {
	System.out.println(emf == null);
	JpaPagingItemReader<SMicrodata> reader = new JpaPagingItemReader<>();
	reader.setEntityManagerFactory(emf);

	reader.setQueryString("select m from SMicrodata m where m.instance.survey=" + survey
		+ "and m.instance.survey.status=13 and m.instance.period!=0 order by m.instance.id desc");

	reader.setPageSize(30);

	return reader;
    }

    @Bean(name = "smProcessor1")
    @StepScope
    public ItemProcessor<SMicrodata, TMicrodata> processor1(@Value("#{jobParameters['survey']}") Long survey) {
	return new MicrodataMigrationProcessor(survey);
    }

    @Bean(name = "smWriter1")
    @StepScope
    public ItemWriter<TMicrodata> writer1() {
	JpaItemWriter<TMicrodata> writer = new JpaItemWriter();
	writer.setEntityManagerFactory(emf);

	return writer;
    }

    @Bean
    public TaskExecutor executor() {
	TaskExecutor executor = new SimpleAsyncTaskExecutor();
	return executor;
    }

    @Bean
    public Tasklet validateMigrationTasklet() {
	return new ValidateSurveyMigration();
    }

}
