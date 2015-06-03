/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.model;

import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.domain.TMicrodata;

/**
 *
 * @author iulian.radulescu
 */
public class MicrodataMigrationProcessorOutput {

    private TMicrodata microdata;
    private ReportingUnitUser reportingUnitUser;

    public MicrodataMigrationProcessorOutput(TMicrodata microdata, ReportingUnitUser reportingUnitUser) {
        this.microdata = microdata;
        this.reportingUnitUser = reportingUnitUser;
    }

    public TMicrodata getMicrodata() {
        return this.microdata;
    }

    public ReportingUnitUser getReportingUnitUser() {
        return this.reportingUnitUser;
    }
}
