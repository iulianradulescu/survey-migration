/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ro.digidata.esop.domain.ReportingUnitUser;

/**
 *
 * @author radulescu
 */
public interface ReportingUnitUserRepository extends CrudRepository<ReportingUnitUser, Long>{
    
    @Query("select r from ReportingUnitUser r where r.reportingUnit.id in (select reportingUnit from Sample s where s.survey=?1)")
    List<ReportingUnitUser> findBySurvey( Long survey );
    
    ReportingUnitUser findByUsernameAndReportingUnitId( String username, Long reportingUnitId);
    
}
