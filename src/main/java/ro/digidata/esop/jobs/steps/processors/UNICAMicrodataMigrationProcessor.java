/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import ro.digidata.esop.domain.Questionnaire;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.enums.QuestionnaireStatus;

/**
 *
 * @author iulian.radulescu
 */
public class UNICAMicrodataMigrationProcessor extends MicrodataMigrationProcessor {

    public UNICAMicrodataMigrationProcessor(Long survey) {
        super(survey);
    }
    
    @Override
    protected Questionnaire processQuestionnaire(Sample sample, SMicrodata input)  {
        Questionnaire quest = questRepository.findBySampleIdAndStatus(sample.getId( ), QuestionnaireStatus.ACTIV );

        if (quest != null && !quest.getQuestType().equals( input.getQuestType( ) ) ) {
            //we need to change the status to 0, and make a new questionnaire
            quest.setStatus(QuestionnaireStatus.INACTIV);
            
            //check to see if the same questionnaire was used in the past
            quest = questRepository.findBySampleIdAndQuestType(sample.getId( ), input.getQuestType( ) );
            
            if ( quest != null ) {
                //reactivate it
                quest.setStatus( QuestionnaireStatus.ACTIV);
            } else {
                quest = null;
            }
        }
        
          if (quest == null) {
            quest = new Questionnaire();

            quest.setQuestType(input.getQuestType( ) );
            quest.setSample( sample );
            quest.setStatus(QuestionnaireStatus.ACTIV );
        }

        return quest;
    }
}
