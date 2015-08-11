/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.SSample;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.domain.StatisticalUnit;
import ro.digidata.esop.domain.StatisticalUnitData;
import ro.digidata.esop.jobs.steps.model.SubSample;
import ro.digidata.esop.repositories.SSampleRepository;
import ro.digidata.esop.repositories.SampleRepository;
import ro.digidata.esop.repositories.StatisticalUnitRepository;
import ro.digidata.esop.services.SurveyService;
import ro.digidata.esop.services.model.PopulationAttribute;
import ro.digidata.esop.services.model.SurveyInfo;

/**
 *
 * @author iulian.radulescu
 */
public class SubSampleMigrationProcessor implements ItemProcessor<SubSample, Sample>, InitializingBean {
    
    protected Logger logger = LoggerFactory.getLogger(SubSampleMigrationProcessor.class);

    private Long survey;
    private SurveyInfo sInfo;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SSampleRepository ssampleRepository;

    @Autowired
    private StatisticalUnitRepository suRepository;

    @Autowired
    private SurveyService surveyService;

    public SubSampleMigrationProcessor(Long survey) {
        this.survey = survey;
        // 
    }

    @Override
    public Sample process(SubSample record) throws Exception {
        Sample s = sampleRepository.findBySurveyAndStatisticalUnitCodeAndStatisticalUnitPopulation(sInfo.id(), record.getQuestInstId(), sInfo.sClass().population().id());

        if (s == null) {
            SSample ss = ssampleRepository.findOne(record.getSample());

            //find the parent
            StatisticalUnit parent = suRepository.findOne(ssampleRepository.findOne(record.getSample()).getMaximalListId());
            
            //we must create it; first check to see if the statistical unit exists
            StatisticalUnit unit = suRepository.findByCodeAndPopulation(record.getQuestInstId(), sInfo.sClass().population().id());
            if (unit == null) {
                unit = new StatisticalUnit();

                unit.setCode(record.getQuestInstId());
                unit.setName(getStatisticalUnitNameFromQuestInfo(record.getQuestInstInfo()));
                unit.setCounty(parent.getCounty());
                unit.setPopulation(sInfo.sClass().population().id());
                unit.setParent(parent.getId());

                //create also the attributes
                unit.addValue(createData(PopulationAttribute.COD, unit.getCode(), unit));
                unit.addValue(createData(PopulationAttribute.DENI, unit.getName(), unit));
                unit.addValue(createData(PopulationAttribute.JUD, "" + unit.getCounty(), unit));
            } else {
                //if parent is not the same log the code and skip the  processing
                if ( parent.getId( ).longValue() != unit.getParent( ).longValue() ) {
                    //we have a situation where the same code is attached to tow different parents; log it and skip it
                    logger.warn("QUEST_INST_ID=" + record.getQuestInstId( ) + " IS ATTACHED TO 2 DIFFERENT PARENT UNITS WITH ID= " + parent.getId( ) +", " + unit.getParent());
                    return null;
                }
            }

            s = new Sample();
            s.setStatisticalUnit(unit);
            s.setOnlineEdit(ss.getOnlineEdit());
            s.setStatus(ss.getStatus());
            s.setSurvey(sInfo.id());

            return s;
        }

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sInfo = surveyService.surveyInfo(survey);
    }

    private String getStatisticalUnitNameFromQuestInfo(String questInfo) {
        if (!questInfo.contains(":")) {
            return questInfo.trim();
        }
        String startQuestInfo = questInfo.substring(0, questInfo.indexOf(":"));

        //these are all possible fields in QUEST_INST_INFO and we try to eliminate all in order only the name to remain
        return startQuestInfo.replaceAll("TIP|FEL|JUD|SIRUTA|CAEN|LOCS|ZONA|GEN|TIP UNITATE|LIMBA", "").trim();
    }

    private StatisticalUnitData createData(PopulationAttribute attribute, String value, StatisticalUnit unit) {
        StatisticalUnitData sud = new StatisticalUnitData();

        sud.setAttribute(attribute.idForPopulation(sInfo.sClass().population()));
        sud.setValue(value);
        sud.setStatisticalUnit(unit);
        sud.setModificationDate(new Date());

        return sud;
    }
}
