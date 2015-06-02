/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.Questionnaire;
import ro.digidata.esop.domain.ReportingUnit;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.TMicrodata;
import ro.digidata.esop.domain.UnitNonResponse;
import ro.digidata.esop.domain.enums.FillMode;
import ro.digidata.esop.domain.enums.QuestionnaireStatus;
import ro.digidata.esop.repositories.QuestionnaireRepository;
import ro.digidata.esop.repositories.ReportingUnitRepository;
import ro.digidata.esop.repositories.ReportingUnitUserRepository;
import ro.digidata.esop.repositories.SampleRepository;

/**
 *
 * @author radulescu
 */
public class MicrodataMigrationProcessor implements ItemProcessor<SMicrodata, TMicrodata> {

    private final Long survey;

    @Autowired
    protected SampleRepository sampleRepository;
    
    @Autowired
    protected ReportingUnitUserRepository ruuRepository;

    @Autowired
    protected QuestionnaireRepository questRepository;

    public MicrodataMigrationProcessor(Long survey) {
	this.survey = survey;
    }

    @Override
    public TMicrodata process(SMicrodata record) throws Exception {
	TMicrodata output = new TMicrodata();

	output.setInstance(record.getInstance());
	output.setVersion(record.getVersion());
	output.setCorrelationResults( record.getCorrelations( ) );
        output.setCorrelationStatus(record.getCorrelation());
	
        output.setFillMode(FillMode.valueOf(record.getFinalSave()).name());
        output.setInterview(record.getInterview());
        output.setLastComputed(record.getLastComputed());
        output.setLastUpdated(record.getLastUpdated());

	Sample sample = sampleRepository.findBySurveyAndStatisticalUnit(survey, record.getSample().getMaximalListId());

	Questionnaire quest = processQuestionnaire(sample, record);

	//set on microdata
	output.setQuestionnaire(quest);

	//handle also the nonresponse
	handleNonResponse( record, sample );
	
	return output;
    }

    protected Questionnaire processQuestionnaire(Sample sample, SMicrodata input) {
	Questionnaire quest = questRepository.findBySampleIdAndQuestType(sample.getId(), input.getQuestType());

	if (quest == null) {
	    quest = new Questionnaire();

	    quest.setQuestType(input.getQuestType());
	    quest.setSample(sample);
	    quest.setStatus(QuestionnaireStatus.ACTIV);
	}

	return quest;
    }
    
    protected void handleNonResponse(SMicrodata input, Sample sample) {

        //do handle the nonresponse
        UnitNonResponse nonresp = new UnitNonResponse( );
        nonresp.setSample( sample );
	sample.addNonResponse( nonresp );

        nonresp.setInstance(input.getInstance().getId());

	//if there is something put it; otherwise put 1
        if (input.getNonresponse() == null && !input.getInstance().isStillCollecting( ) ) {
            nonresp.setNonresponse( 9 );
        } else {
	    //what will happen now if at the end of the collection we do not edit enything
	    //perhaps is better to put default to 9, and change it when we have an interview or something explicitly set
	    nonresp.setNonresponse( input.getNonresponse() != null ? input.getNonresponse() : 1 ); 
        }
    }
}
