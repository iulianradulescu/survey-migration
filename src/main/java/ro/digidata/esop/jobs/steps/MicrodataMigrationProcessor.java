/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.Questionnaire;
import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.TMicrodata;
import ro.digidata.esop.domain.UnitNonResponse;
import ro.digidata.esop.domain.enums.FillMode;
import ro.digidata.esop.domain.enums.QuestionnaireStatus;
import ro.digidata.esop.jobs.steps.model.MicrodataMigrationProcessorOutput;
import ro.digidata.esop.jobs.steps.model.authorization.Authorization;
import ro.digidata.esop.jobs.steps.model.authorization.AuthorizationUtils;
import ro.digidata.esop.jobs.steps.model.authorization.QuestionnaireType;
import ro.digidata.esop.jobs.steps.model.authorization.SurveyType;
import ro.digidata.esop.jobs.steps.model.authorization.UnitType;
import ro.digidata.esop.repositories.QuestionnaireRepository;
import ro.digidata.esop.repositories.ReportingUnitRepository;
import ro.digidata.esop.repositories.ReportingUnitUserRepository;
import ro.digidata.esop.repositories.SampleRepository;

/**
 *
 * @author radulescu
 */
public class MicrodataMigrationProcessor implements ItemProcessor<SMicrodata, MicrodataMigrationProcessorOutput> {

    private final Long survey;

    @Autowired
    protected ReportingUnitRepository ruRepository;

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
    public MicrodataMigrationProcessorOutput process(SMicrodata record) throws Exception {
        TMicrodata output = new TMicrodata();

        output.setInstance(record.getInstance());
        output.setVersion(record.getVersion());
        output.setCorrelationResults(record.getCorrelations());
        output.setCorrelationStatus(record.getCorrelation());

        output.setFillMode(FillMode.valueOf(record.getFinalSave()).name());
        output.setInterview(record.getInterview());
        output.setLastComputed(record.getLastComputed());
        output.setLastUpdated(record.getLastUpdated());
        
        Sample sample = sampleRepository.findBySurveyAndStatisticalUnit(survey, record.getSample().getMaximalListId());//processSample( record );

        Questionnaire quest = processQuestionnaire(sample, record);
        
        ReportingUnitUser ruu = null;
        
        if ( record.getRespondent( ) != null ) {
            ruu = processAuthorization(quest, record.getRespondent(  ) );
        }

        //set on microdata
        output.setQuestionnaire(quest);

        //handle also the nonresponse
        handleNonResponse(record, sample);

        return new MicrodataMigrationProcessorOutput(output, ruu);
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
        UnitNonResponse nonresp = new UnitNonResponse();
        nonresp.setSample(sample);
        sample.addNonResponse(nonresp);

        nonresp.setInstance(input.getInstance().getId());

        //if there is something put it; otherwise put 1
        if (input.getNonresponse() == null && !input.getInstance().isStillCollecting()) {
            nonresp.setNonresponse(9);
        } else {
            //what will happen now if at the end of the collection we do not edit enything
            //perhaps is better to put default to 9, and change it when we have an interview or something explicitly set
            nonresp.setNonresponse(input.getNonresponse() != null ? input.getNonresponse() : 1);
        }
    }

    protected ReportingUnitUser processAuthorization(Questionnaire questionnaire, String username) {
        //we need to see if the respondent is defined in REPORTING_UNIT_USER; if not insert it, is yes,update the AUTHORIZATION field
        ReportingUnitUser user = ruuRepository.findByUsernameAndReportingUnitId(username, questionnaire.getSample().getReportingUnit());

        if (user != null) {
            //it means the user was not deactivated in the meantime (still in MAXIMAL_LIST_USER which was migrated to REPORTING_UNIT_USER).
            Authorization auth = null;
            if (user.getAuthorization() != null) {
                auth = AuthorizationUtils.unmarshallAuthorization(user.getAuthorization());
            }

            if (auth == null) {
                auth = new Authorization();
            }

            //add the new configuration and then save it back
            UnitType unit = null;
            SurveyType survey = null;
            QuestionnaireType quest = null;

            for (UnitType u : auth.getUnit()) {
                if (u.getId() == questionnaire.getSample().getStatisticalUnit()) {
                    unit = u;
                    break;
                }
            }

            if (unit == null) {
                unit = new UnitType();
                unit.setId(questionnaire.getSample().getStatisticalUnit());
                auth.getUnit().add(unit);
            }

            for (SurveyType s : unit.getSurvey()) {
                if (s.getId() == this.survey) {
                    survey = s;
                    break;
                }
            }

            if (survey == null) {
                survey = new SurveyType();
                survey.setId((long) this.survey);
                unit.getSurvey().add(survey);
            }

            for (QuestionnaireType q : survey.getQuestionnaire()) {
                if (q.getId() == (long) questionnaire.getId()) {
                    quest = q;
                    break;
                }
            }

            if (quest == null) {
                quest = new QuestionnaireType();
                quest.setId((long) questionnaire.getId());
                survey.getQuestionnaire().add(quest);

            }

            String xml = AuthorizationUtils.marshallAuthorization(auth);
            user.setAuthorization(xml);
        }

        return user;
    }
}
