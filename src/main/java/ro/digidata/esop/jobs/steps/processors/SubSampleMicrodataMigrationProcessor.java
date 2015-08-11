/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.Sample;

/**
 *
 * @author iulian.radulescu
 */
public class SubSampleMicrodataMigrationProcessor extends MicrodataMigrationProcessor {

    public SubSampleMicrodataMigrationProcessor(Long survey) {
        super(survey);
    }
    
    protected Sample processSample( SMicrodata record ) {
        //logger.info("Process record for quest_inst_id=" + record.getQuestInst( ) );
        return sampleRepository.findBySurveyAndStatisticalUnitCodeAndStatisticalUnitPopulation(survey, record.getQuestInst( ), sInfo.sClass().population().id());
    }
}
