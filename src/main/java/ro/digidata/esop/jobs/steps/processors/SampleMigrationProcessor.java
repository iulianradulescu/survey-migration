/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.ReportingUnit;
import ro.digidata.esop.domain.SSample;
import ro.digidata.esop.domain.Sample;
import ro.digidata.esop.repositories.ReportingUnitRepository;
import ro.digidata.esop.repositories.StatisticalUnitRepository;

/**
 *
 * @author radulescu
 */
public class SampleMigrationProcessor implements ItemProcessor<SSample, Sample> {

    private Long survey;

    @Autowired
    protected ReportingUnitRepository ruRepository;
    
     @Autowired
    protected StatisticalUnitRepository suRepository;

    public SampleMigrationProcessor(Long survey) {
        this.survey = survey;
    }

    @Override
    public Sample process(SSample input) throws Exception {
        Sample sample = new Sample();

        sample.setOnlineEdit(input.getOnlineEdit());
        sample.setStatus(input.getStatus());

        sample.setSurvey(survey);
        sample.setStatisticalUnit(suRepository.findOne( input.getMaximalListId() ));

        //check if the reporting unit id exists; if not, put null
        ReportingUnit reportingUnit = ruRepository.findOne(input.getMaximalListId());

        if (reportingUnit != null) {
            sample.setReportingUnit( reportingUnit );
        }

        return sample;
    }

}
