/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.StatisticalUnit;
import ro.digidata.esop.domain.StatisticalUnitSurveyUser;
import ro.digidata.esop.jobs.steps.model.SubSampleSurveyUser;
import ro.digidata.esop.repositories.StatisticalUnitRepository;
import ro.digidata.esop.services.SurveyService;
import ro.digidata.esop.services.model.SurveyInfo;

/**
 *
 * @author iulian.radulescu
 */
public class SubSampleStatisticalUnitSurveyUserProcessor implements ItemProcessor<SubSampleSurveyUser, StatisticalUnitSurveyUser>, InitializingBean {

    private Long survey;
    private SurveyInfo sInfo;

    @Autowired
    private SurveyService surveyService;
    
    @Autowired
    private StatisticalUnitRepository suRepository;

    public SubSampleStatisticalUnitSurveyUserProcessor(Long survey) {
        this.survey = survey;
    }

    @Override
    public StatisticalUnitSurveyUser process(SubSampleSurveyUser input) throws Exception {
        //search for the  statistical unit based on the code and population
        StatisticalUnit su = suRepository.findByCodeAndPopulation( input.getQuestInstId( ), sInfo.sClass().population().id());
        
        if ( su != null ) {
            StatisticalUnitSurveyUser susu = new StatisticalUnitSurveyUser();
            susu.setSurvey( survey );
            susu.setUnit( su.getId( ) );
            susu.setUsername( input.getUsername( ) );
            
            return susu;
        }
        
        //this should not happen
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sInfo = surveyService.surveyInfo(survey);
    }

}
