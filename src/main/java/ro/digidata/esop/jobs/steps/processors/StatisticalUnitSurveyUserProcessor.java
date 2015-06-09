/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import org.hibernate.stat.Statistics;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.MaximalListSurveyUser;
import ro.digidata.esop.domain.StatisticalUnitSurveyUser;
import ro.digidata.esop.repositories.StatisticalUnitSurveyUserRepository;

/**
 *
 * @author iulian.radulescu
 */
public class StatisticalUnitSurveyUserProcessor implements ItemProcessor<MaximalListSurveyUser, StatisticalUnitSurveyUser> {

    @Autowired
    private StatisticalUnitSurveyUserRepository susuRepository;

    public StatisticalUnitSurveyUserProcessor() {
        
    }
   
    @Override
    public StatisticalUnitSurveyUser process(MaximalListSurveyUser input) throws Exception {
        //check to see if there si an allocation done already
        StatisticalUnitSurveyUser susu = susuRepository.findByUnitAndSurvey( input.getUnit( ), input.getSurvey( ));
        if (susu == null) {
                //do an allocation
                susu = new StatisticalUnitSurveyUser();

                susu.setUnit( input.getUnit( ) );
                susu.setUsername(input.getUsername());
                susu.setSurvey(input.getSurvey());
                
                return susu;
            }
        
        //null means skip it
        return null;
    }

}
