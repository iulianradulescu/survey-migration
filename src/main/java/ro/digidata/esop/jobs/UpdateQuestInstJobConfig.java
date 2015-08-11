/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.validation.BindException;

/**
 *
 * @author radulescu
 */
@Configuration
public class UpdateQuestInstJobConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean(name = "updateQuestInstJob")
    public Job job() {
	return jobs.get("updateQuestInstJob").start(step()).build();
    }

    @Bean
    @JobScope
    public Step step() {
	//System.out.println("Step configuration for updateQuestInstJob");
	return steps.get("steps").<QuestInstStructure, QuestInstStructure>chunk(1)
		.reader(reader(null))
		.processor(processor())
		.writer(writer(null))
		.listener(writerListener())//it will be properly wired at execution time
		.build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<QuestInstStructure> reader(@Value("#{jobParameters['inputFile']}") String inputFile) {
	FlatFileItemReader<QuestInstStructure> reader = new FlatFileItemReader<>();
	reader.setResource(new PathResource(inputFile));

	DefaultLineMapper<QuestInstStructure> mapper = new DefaultLineMapper<>();
	mapper.setLineTokenizer(new DelimitedLineTokenizer(";"));
	mapper.setFieldSetMapper(new FieldSetMapper<QuestInstStructure>() {

	    @Override
	    public QuestInstStructure mapFieldSet(FieldSet fs) throws BindException {
		return new QuestInstStructure(fs.readString(0), fs.readString(1), fs.readString(2));
	    }
	});
	reader.setLineMapper(mapper);

	return reader;
    }

    @Bean
    public ItemProcessor<QuestInstStructure, QuestInstStructure> processor() {
	return new ItemProcessor<QuestInstStructure, QuestInstStructure>() {

	    @Override
	    public QuestInstStructure process(QuestInstStructure i) throws Exception {
		return i;
	    }
	};
    }

    @Bean
    @StepScope
    public ItemWriter<QuestInstStructure> writer(DataSource dataSource) {

	JdbcBatchItemWriter<QuestInstStructure> writer = new JdbcBatchItemWriter<>();
	writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<QuestInstStructure>());
	writer.setSql("UPDATE OLD_MICRODATA SET QUEST_INST_ID=:newQuestInst WHERE QUEST_INST_ID=:currentQuestInst AND"
		+ " SAMPLE_ID IN (SELECT S.ID FROM OLD_SAMPLE S, MAXIMAL_LIST ML WHERE S.MAXIMAL_LIST_ID = ML.CHEIE AND ML.COD=:companyCode)");

	writer.setDataSource(dataSource);
	return writer;
    }

    @Bean
    public ItemWriteListener writerListener() {
	return new ItemWriteListener<QuestInstStructure>() {

	    @Override
	    public void beforeWrite(List<? extends QuestInstStructure> list) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		//System.out.println("ITEMS TO WRITE: " + list.size());
	    }

	    @Override
	    public void afterWrite(List<? extends QuestInstStructure> list) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		//System.out.println("ITEMS WROTE: " + list.size());
	    }

	    @Override
	    public void onWriteError(Exception exception, List<? extends QuestInstStructure> list) {
		System.out.println("ERROR ON: " + list.toString());
	    }

	};
    }

    public static class QuestInstStructure {

	private final String currentQuestInst;
	private final String companyCode;
	private final String newQuestInst;

	public QuestInstStructure(String newQuestInst, String currentQuestInst, String companyCode) {
	    this.newQuestInst = newQuestInst;
	    this.companyCode = companyCode;
	    this.currentQuestInst = currentQuestInst;
	}

	public String getCurrentQuestInst() {
	    return this.currentQuestInst;
	}

	public String getCompanyCode() {
	    return this.companyCode;
	}

	public String getNewQuestInst() {
	    return this.newQuestInst;
	}

	public String toString() {
	    return new StringBuilder("[QUEST_INST_ID=")
		    .append(currentQuestInst)
		    .append(", COD=")
		    .append(companyCode)
		    .append(", CHEIE_FALSA=")
		    .append(newQuestInst)
		    .append("]").toString();
	}
    }
}
