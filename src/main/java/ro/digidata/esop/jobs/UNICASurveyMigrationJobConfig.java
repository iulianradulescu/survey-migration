/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.jobs.steps.UNICAMicrodataMigrationProcessor;
import ro.digidata.esop.jobs.steps.model.MicrodataMigrationProcessorOutput;

/**
 *
 * @author iulian.radulescu
 */
//@Configuration
public class UNICASurveyMigrationJobConfig extends SurveyMigrationJobConfig {
    
     @Bean(name = "smProcessor1-UNICA")
    @StepScope
    public ItemProcessor<SMicrodata, MicrodataMigrationProcessorOutput> processor1(@Value("#{jobParameters['survey']}") Long survey) {
        return new UNICAMicrodataMigrationProcessor(survey);
    }
}
