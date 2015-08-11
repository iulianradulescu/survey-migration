/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.tasklets;

import java.util.ArrayList;
import java.util.List;
import ro.digidata.esop.services.model.MigratedEntity;
import ro.digidata.esop.services.model.SurveyInfo;

/**
 *
 * @author iulian.radulescu
 */
public class ValidateS3SurveyMigration extends ValidateSurveyMigration {
    
    @Override
    protected List<CountResult> countQuestionnaires(SurveyInfo sInfo ) {
        List<CountResult> results = new ArrayList<>( );
        
        //count S3
        results.add(new CountResult(MigratedEntity.QUESTIONNAIRE.name()+"=S3",
                 smicrodataRepository.countQuestionnaires(sInfo.id(),"S3"),
                qRepository.countQuestionnaires(sInfo.id(),"S3")));
        
        //count S3_ANEXA
        results.add( new CountResult(MigratedEntity.QUESTIONNAIRE.name()+"=S3_ANEXA",
                 smicrodataRepository.countSubSampleQuestionnaires(sInfo.id(),"S3_ANEXA"),
                qRepository.countQuestionnaires(sInfo.id(),"S3_ANEXA")));
        
        return results;
    }
    
    @Override
    protected CountResult countNonResponse(int nonresponse, SurveyInfo sInfo) {
        if (nonresponse != 9) {
            return new CountResult(MigratedEntity.NONRESPONSE.name()+"="+nonresponse,
                    smicrodataRepository.countByInstanceSurveyIdAndNonresponseAndQuestType(sInfo.id(), nonresponse,"S3"),
                    unrRepository.countBySampleSurveyAndNonresponse(sInfo.id(), nonresponse));
        } else {
            //for non-response 9 we do it differently, meaningl if there is no non-response set, on migration we set it to 9, but only if the survey is finished
            return new CountResult(MigratedEntity.NONRESPONSE.name()+"=9",
                    smicrodataRepository.coundNonResponse9S3(sInfo.id()),
                    unrRepository.countBySampleSurveyAndNonresponse(sInfo.id(), nonresponse));
        }
    }
}
