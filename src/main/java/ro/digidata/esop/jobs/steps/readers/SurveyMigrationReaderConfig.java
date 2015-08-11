/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.readers;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import ro.digidata.esop.domain.MaximalListSurveyUser;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.SSample;
import ro.digidata.esop.jobs.steps.model.SubSample;
import ro.digidata.esop.jobs.steps.model.SubSampleSurveyUser;

/**
 * reader configurations used in survey migration
 *
 * @author iulian.radulescu
 */
@Configuration
public class SurveyMigrationReaderConfig {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private DataSource dataSource;

    @Bean(destroyMethod = "")
    @StepScope
    public ItemStreamReader<SSample> sampleReader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<SSample> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select s from SSample s where s.survey=" + survey);

        reader.setPageSize(1000);

        return reader;
    }

    @Bean(destroyMethod = "")
    @StepScope
    public ItemStreamReader<SubSample> subSampleReader(@Value("#{jobParameters['survey']}") Long survey) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();

        reader.setSql("select distinct sample_id, quest_inst_id, quest_inst_info, instance_id from old_microdata m, old_sample s, instance i "
                + "where m.sample_id=s.id and m.instance_id=i.id and s.survey_id=" + survey + " and i.period !=0 and quest_inst_info is not null and quest_inst_info != 'null' order by instance_id desc");
        reader.setDataSource(dataSource);

        reader.setVerifyCursorPosition( false );
        reader.setRowMapper(new RowMapper() {

            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                return new SubSample(rs.getLong(1), rs.getString(2), rs.getString(3));
            }
        });

        return reader;
    }
     @Bean(destroyMethod = "")
    @StepScope
    public ItemStreamReader<SubSampleSurveyUser> subSampleSurveyUserReader(@Value("#{jobParameters['survey']}") Long survey) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();

        reader.setSql("  select m.quest_inst_id, mlsu.maximal_list_id, mlsu.username from Maximal_List_Survey_User mlsu, Survey s, Instance i, " +
                            " Old_Microdata m, Old_Sample os, Maximal_List ml where ml.cheie=mlsu.maximal_list_id and os.maximal_list_id=ml.cheie " +
                            "  and m.sample_id=os.id and s.id = mlsu.survey_id and s.id=i.survey_id and m.instance_id=i.id and i.period<>0 and s.id=" + survey );
        reader.setDataSource(dataSource);

        reader.setVerifyCursorPosition( false );
        reader.setRowMapper(new RowMapper() {

            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                return new SubSampleSurveyUser(rs.getLong(2), rs.getString(1), rs.getString(3));
            }
        });

        return reader;
    }

    @Bean(destroyMethod = "")
    @StepScope
    public ItemStreamReader<MaximalListSurveyUser> maximalListSurveyUserReader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<MaximalListSurveyUser> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select m from MaximalListSurveyUser m where m.survey=" + survey);

        reader.setPageSize(1000);

        return reader;
    }

    @Bean(destroyMethod = "")
    @StepScope
    public ItemStreamReader<SMicrodata> microdataReader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<SMicrodata> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select m from SMicrodata m where m.instance.survey=" + survey
                + "and m.instance.survey.status=13 and m.instance.period!=0 order by m.instance.id desc, m.sample.id asc");

        reader.setPageSize(30);

        return reader;
    }

    @Bean(destroyMethod = "")
    @StepScope
    public ItemStreamReader<SMicrodata> microdataS3Reader(@Value("#{jobParameters['survey']}") Long survey) {
        JpaPagingItemReader<SMicrodata> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("select m from SMicrodata m where m.instance.survey=" + survey
                + "and m.instance.survey.status=13 and m.instance.period!=0 and m.questType='S3' order by m.instance.id desc, m.sample.id asc");

        reader.setPageSize(30);

        return reader;
    }
}
