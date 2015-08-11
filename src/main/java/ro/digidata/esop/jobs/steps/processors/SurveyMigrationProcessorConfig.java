/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.digidata.esop.domain.MaximalListSurveyUser;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.SSample;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.StatisticalUnitSurveyUser;
import ro.digidata.esop.jobs.steps.model.MicrodataMigrationProcessorOutput;
import ro.digidata.esop.jobs.steps.model.SubSample;
import ro.digidata.esop.jobs.steps.model.SubSampleSurveyUser;

/**
 *
 * @author iulian.radulescu
 */
@Configuration
public class SurveyMigrationProcessorConfig {

    @Bean
    @StepScope
    public ItemProcessor<SSample, Sample> sampleProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new SampleMigrationProcessor(survey);
    }

    @Bean
    @StepScope
    public ItemProcessor<SubSample, Sample> subSampleProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new SubSampleMigrationProcessor(survey);
    }

    @Bean
    @StepScope
    public ItemProcessor<MaximalListSurveyUser, StatisticalUnitSurveyUser> statisticalUnitSurveyUserProcessor() {
        return new StatisticalUnitSurveyUserProcessor();
    }

    @Bean
    @StepScope
    public ItemProcessor<SMicrodata, MicrodataMigrationProcessorOutput> microdataProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new MicrodataMigrationProcessor(survey);
    }

    @Bean
    @StepScope
    public ItemProcessor<SMicrodata, MicrodataMigrationProcessorOutput> subSampleMicrodataProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new SubSampleMicrodataMigrationProcessor(survey);
    }
    
    @Bean
    @StepScope
    public ItemProcessor<SubSampleSurveyUser, StatisticalUnitSurveyUser> subSampleStatisticalUnitSurveyUserProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new SubSampleStatisticalUnitSurveyUserProcessor(survey);
    }

    @Bean
    @StepScope
    public ItemProcessor<SMicrodata, MicrodataMigrationProcessorOutput> unicaMicrodataProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new UNICAMicrodataMigrationProcessor(survey);
    }

    @Bean
    @StepScope
    public ItemProcessor<SMicrodata, MicrodataMigrationProcessorOutput> s3MicrodataProcessor(@Value("#{jobParameters['survey']}") Long survey) {
        return new S3MicrodataMigrationProcessor(survey);
    }
}
