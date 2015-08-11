/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.writers;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.StatisticalUnitSurveyUser;
import ro.digidata.esop.domain.TMicrodata;
import ro.digidata.esop.jobs.steps.model.MicrodataMigrationProcessorOutput;

/**
 *
 * @author iulian.radulescu
 */
@Configuration
public class SurveyMigrationWriterConfig {

    @Autowired
    private EntityManagerFactory emf;

    @Bean
    @StepScope
    public ItemWriter<Sample> sampleWriter() {
        JpaItemWriter<Sample> writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);

        return writer;
    }

    @Bean
    @StepScope
    public ItemWriter<StatisticalUnitSurveyUser> statisticalUnitSurveyUserWriter() {
        JpaItemWriter<StatisticalUnitSurveyUser> writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);

        return writer;
    }

    @Bean
    @StepScope
    public ItemWriter<MicrodataMigrationProcessorOutput> microdataProcessorOutputWriter() {
        ItemWriter<MicrodataMigrationProcessorOutput> writer = new ItemWriter<MicrodataMigrationProcessorOutput>() {

            JpaItemWriter<TMicrodata> microdataWriter = new JpaItemWriter();
            JpaItemWriter<ReportingUnitUser> ruuWriter = new JpaItemWriter();

            {
                microdataWriter.setEntityManagerFactory(emf);
                ruuWriter.setEntityManagerFactory(emf);
            }

            @Override
            public void write(List<? extends MicrodataMigrationProcessorOutput> list) throws Exception {
                //create two listst for the 2 writers
                List<TMicrodata> microdataList = new ArrayList<>();
                List<ReportingUnitUser> ruuList = new ArrayList<>();

                for (MicrodataMigrationProcessorOutput output : list) {
                    microdataList.addAll(output.getMicrodata());
                    if (output.getReportingUnitUser() != null) {
                        ruuList.addAll(output.getReportingUnitUser());
                    }
                }

                microdataWriter.write(microdataList);
                ruuWriter.write(ruuList);
            }
        };

        return writer;
    }
}
