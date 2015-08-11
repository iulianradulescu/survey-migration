/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author iulian.radulescu
 */
@Entity
public class Sample {

    @Id
    @SequenceGenerator(name = "SQSAMPLE", sequenceName = "SQ_SAMPLE", allocationSize = 1)
    @GeneratedValue(generator = "SQSAMPLE")
    private Long id;

    private int status;

    @Column(name = "ONLINE_EDIT")
    private String onlineEdit;

    //@ManyToOne
    //@JoinColumn(name = "SURVEY_ID")
    @Column(name = "SURVEY_ID")
    private long survey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STATISTICAL_UNIT_ID")
    private StatisticalUnit statisticalUnit;

    @ManyToOne
    @JoinColumn(name = "REPORTING_UNIT_ID")
    private ReportingUnit reportingUnit;

    @OneToMany(mappedBy = "sample", cascade = {CascadeType.ALL})
    private Set<UnitNonResponse> nonResponses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOnlineEdit() {
        return onlineEdit;
    }

    public void setOnlineEdit(String onlineEdit) {
        this.onlineEdit = onlineEdit;
    }

    public void setSurvey(long survey) {
        this.survey = survey;
    }

    public void setStatisticalUnit(StatisticalUnit statisticalUnit) {
        this.statisticalUnit = statisticalUnit;
    }

    public StatisticalUnit getStatisticalUnit() {
        return this.statisticalUnit;
    }

    public void setReportingUnit(ReportingUnit reportingUnit) {
        this.reportingUnit = reportingUnit;
    }

    public ReportingUnit getReportingUnit() {
        return this.reportingUnit;
    }

    public void addNonResponse(UnitNonResponse nonResponse) {
        if (nonResponses == null) {
            nonResponses = new TreeSet<UnitNonResponse>();
        }
        nonResponses.add(nonResponse);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof Sample)) {
            return false;
        }

        Sample other = (Sample) object;

        if (other.id == null || this.id == null) {
            return other.survey == this.survey && other.statisticalUnit == this.statisticalUnit;
        } else {
            return other.id == this.id;
        }
    }

}
