/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.Repository;
import ro.digidata.esop.domain.StatisticalUnitSurveyUser;

/**
 *
 * @author iulian.radulescu
 */
public interface StatisticalUnitSurveyUserRepository extends Repository<StatisticalUnitSurveyUser, Long>{
    
    StatisticalUnitSurveyUser findByUnitAndSurvey( Long statisticalUnit, Long survey );
}
