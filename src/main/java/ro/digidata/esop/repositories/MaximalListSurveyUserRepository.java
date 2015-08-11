/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.Repository;
import ro.digidata.esop.domain.MaximalListSurveyUser;

/**
 *
 * @author iulian.radulescu
 */
public interface MaximalListSurveyUserRepository extends Repository<MaximalListSurveyUser, Long> {
    
    Long countBySurvey( Long survey );
}
