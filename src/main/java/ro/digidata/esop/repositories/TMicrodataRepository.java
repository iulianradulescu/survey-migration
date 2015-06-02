/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.digidata.esop.domain.TMicrodata;

/**
 *
 * @author radulescu
 */
public interface TMicrodataRepository extends CrudRepository<TMicrodata, Long>{
    
    Long deleteByInstanceSurveyId( Long survey );
}
