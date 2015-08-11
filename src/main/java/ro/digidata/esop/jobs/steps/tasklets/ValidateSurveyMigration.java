/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.tasklets;

import dnl.utils.text.table.TextTable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.repositories.MaximalListSurveyUserRepository;
import ro.digidata.esop.repositories.QuestionnaireRepository;
import ro.digidata.esop.repositories.SMicrodataRepository;
import ro.digidata.esop.repositories.SSampleRepository;
import ro.digidata.esop.repositories.SampleRepository;
import ro.digidata.esop.repositories.StatisticalUnitSurveyUserRepository;
import ro.digidata.esop.repositories.TMicrodataRepository;
import ro.digidata.esop.repositories.UnitNonResponseRepository;
import ro.digidata.esop.services.SurveyService;
import ro.digidata.esop.services.model.MigratedEntity;
import ro.digidata.esop.services.model.SurveyInfo;

/**
 *
 * @author radulescu
 */
public class ValidateSurveyMigration implements Tasklet {

    protected Logger logger = LoggerFactory.getLogger(ValidateSurveyMigration.class);

    @Autowired
    protected SSampleRepository ssampleRepository;

    @Autowired
    protected SMicrodataRepository smicrodataRepository;

    @Autowired
    protected SampleRepository sampleRepository;

    @Autowired
    protected QuestionnaireRepository qRepository;

    @Autowired
    protected TMicrodataRepository tmicrodataRepository;

    @Autowired
    protected UnitNonResponseRepository unrRepository;

    @Autowired
    protected StatisticalUnitSurveyUserRepository susuRepository;

    @Autowired
    protected MaximalListSurveyUserRepository mlsuRepository;

    @Autowired
    protected SurveyService surveyService;

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        Long survey = (Long) cc.getStepContext().getJobParameters().get("survey");

        SurveyInfo sInfo = surveyService.surveyInfo(survey);

        List<CountResult> results = new ArrayList<>();

        results.add(countSample(sInfo));
        results.addAll(countQuestionnaires(sInfo));
        results.add(countMicrodata(sInfo));

        for (int nonresponse = 1; nonresponse <= 9; nonresponse++) {
           results.add(countNonResponse(nonresponse, sInfo));
        }

        results.add(countSUSU(sInfo));

        printResults( results );
        return RepeatStatus.FINISHED;
    }

    protected CountResult countSample(SurveyInfo sInfo) {
        return new CountResult(MigratedEntity.SAMPLE.name(),
                ssampleRepository.countBySurveyId(sInfo.id()),
                sampleRepository.countBySurvey(sInfo.id())
        );
    }

    protected List<CountResult> countQuestionnaires(SurveyInfo sInfo) {
        List<CountResult> results = new ArrayList<>( );
        results.add(new CountResult(MigratedEntity.QUESTIONNAIRE.name(),
                smicrodataRepository.countAllQuestionnaires(sInfo.id()) * sInfo.getNoOfQuestionnaires(),
                qRepository.countBySampleSurvey(sInfo.id())));
        
        return results;
    }

    protected CountResult countMicrodata(SurveyInfo sInfo) {
        return new CountResult(MigratedEntity.MICRODATA.name(),
                smicrodataRepository.countMicrodata(sInfo.id()),
                tmicrodataRepository.countByInstanceSurveyId(sInfo.id())
        );
    }

    protected CountResult countNonResponse(int nonresponse, SurveyInfo sInfo) {
        if (nonresponse != 9) {
            return new CountResult(MigratedEntity.NONRESPONSE.name()+"="+nonresponse,
                    smicrodataRepository.countByInstanceSurveyIdAndNonresponse(sInfo.id(), nonresponse),
                    unrRepository.countBySampleSurveyAndNonresponse(sInfo.id(), nonresponse));
        } else {
            //for non-response 9 we do it differently, meaningl if there is no non-response set, on migration we set it to 9, but only if the survey is finished
            return new CountResult(MigratedEntity.NONRESPONSE.name()+"=9",
                    smicrodataRepository.coundNonResponse9(sInfo.id()),
                    unrRepository.countBySampleSurveyAndNonresponse(sInfo.id(), nonresponse));
        }
    }

    protected CountResult countSUSU(SurveyInfo sInfo) {
        return new CountResult(MigratedEntity.STATISTICAL_UNIT_SURVEY_USER.name(),
                mlsuRepository.countBySurvey(sInfo.id()),
                susuRepository.countBySurvey(sInfo.id())
        );
    }

    protected void printResults(List<CountResult> results) {
        String[] columns = {"ENTITY", "EXPECTED", "FOUND"};
        
        String[][] data = new String[results.size( )][3];
        
        for ( int row = 0; row < results.size(); row++) {
            data[row][0] = results.get(row).entity;
            data[row][1] = results.get(row).expected.toString();
            data[row][2] = results.get(row).found.toString();
        }
        
        TextTable tt = new TextTable(columns, data);                                     
        tt.printTable();  
    }

    protected static class CountResult {

        protected Long expected;
        protected Long found;

        protected String entity;

        public CountResult(String entity, Long expected, Long found) {
            this.entity = entity;
            this.expected = expected;
            this.found = found;
        }
    }

}
