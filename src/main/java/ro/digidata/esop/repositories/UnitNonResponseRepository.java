/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.Repository;
import ro.digidata.esop.domain.UnitNonResponse;

/**
 *
 * @author iulian.radulescu
 */
public interface UnitNonResponseRepository extends Repository<UnitNonResponse, Long>{
    
    Long countBySampleSurveyAndNonresponse( Long survey, int nonresponse );
}
