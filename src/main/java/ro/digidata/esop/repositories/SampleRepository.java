/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.digidata.esop.domain.Sample;

/**
 *
 * @author radulescu
 */
public interface SampleRepository extends CrudRepository<Sample, Long> {
    
    Sample findBySurveyAndStatisticalUnitId(Long survey, Long statisticalUnit );
    
    Sample findBySurveyAndStatisticalUnitCodeAndStatisticalUnitPopulation( Long survey, String code, int population );
    
    Long countBySurvey( long survey );
    
    Long deleteBySurvey(Long survey );
    
}
