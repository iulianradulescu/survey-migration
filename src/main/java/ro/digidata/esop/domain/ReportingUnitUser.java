/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name = "REPORTING_UNIT_USER")
@NamedQueries({
    @NamedQuery(name = "ReportingUnitUser.findByUsernameAndReportingUnit", query = "select r from ReportingUnitUser r where r.username=?1 and r.reportingUnit.id=?2"),
    @NamedQuery(name = "ReportingUnitUser.findBySurvey", query = "select r from ReportingUnitUser r where r.reportingUnit.id in (select reportingUnit from Sample s where s.survey=:survey) ")
})
public class ReportingUnitUser {

    @Id
    @SequenceGenerator(name = "SQREPUNITUSER", sequenceName = "SQ#REPORTING_UNIT_USER", allocationSize = 1)
    @GeneratedValue(generator = "SQREPUNITUSER")
    private int id;

    @ManyToOne
    @JoinColumn(name = "REPORTING_UNIT_ID")
    private ReportingUnit reportingUnit;

    private String username;

    @Lob
    private String authorization;

    public ReportingUnit getReportingUnit() {
        return reportingUnit;
    }

    public void setReportingUnit(ReportingUnit reportingUnit) {
        this.reportingUnit = reportingUnit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

}
