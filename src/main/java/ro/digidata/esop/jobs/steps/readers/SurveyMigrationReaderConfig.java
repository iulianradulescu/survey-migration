/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.readers;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.digidata.esop.domain.MaximalListSurveyUser;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.SSample;

/**
 * reader configurations used in survey migration
 *
 * @author iulian.radulescu
 */
@Configuration
public class SurveyMigrationReaderConfig {

    @Autowired
    private EntityManagerFactory emf;

    @Bean
    @StepScope
    public ItemStreamReader<SSample> sampleReader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<SSample> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select s from SSample s where s.survey=" + survey);

        reader.setPageSize(1000);

        return reader;
    }

    @Bean
    @StepScope
    public ItemStreamReader<MaximalListSurveyUser> maximalListSurveyUserReader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<MaximalListSurveyUser> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select m from MaximalListSurveyUser m where m.survey=" + survey);

        reader.setPageSize(1000);

        return reader;
    }
    
    @Bean
    @StepScope
    public ItemStreamReader<SMicrodata> microdataReader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<SMicrodata> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select m from SMicrodata m where m.instance.survey=" + survey
                + "and m.instance.survey.status=13 and m.instance.period!=0 order by m.instance.id desc, m.sample.id asc");

        reader.setPageSize(30);

        return reader;
    }
}
