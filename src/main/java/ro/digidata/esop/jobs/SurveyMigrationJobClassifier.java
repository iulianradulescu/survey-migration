/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.batch.core.Job;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;
import ro.digidata.esop.services.model.SurveyClass;

/**
 *
 * @author iulian.radulescu
 */
@Component
public class SurveyMigrationJobClassifier implements Classifier<SurveyClass, Job> {
    
    @Resource
    private Map<String, Job> jobs = new HashMap<String, Job>();

    @Override
    public Job classify(SurveyClass sClass) {
        return jobs.get( sClass.jobName() ); 
    }

}
