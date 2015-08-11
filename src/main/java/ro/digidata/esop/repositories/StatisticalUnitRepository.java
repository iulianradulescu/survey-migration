/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.digidata.esop.domain.StatisticalUnit;

/**
 *
 * @author iulian.radulescu
 */
public interface StatisticalUnitRepository extends CrudRepository<StatisticalUnit, Long>{
    
    StatisticalUnit findByCodeAndPopulation(String code, int population);
}
