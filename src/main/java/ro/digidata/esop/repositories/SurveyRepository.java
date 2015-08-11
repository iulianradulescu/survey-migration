/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.digidata.esop.domain.Survey;

/**
 *
 * @author iulian.radulescu
 */
public interface SurveyRepository extends CrudRepository<Survey, Long>{
    
}
