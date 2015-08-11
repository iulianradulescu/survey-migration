/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.model;

import java.util.ArrayList;
import java.util.List;
import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.domain.TMicrodata;

/**
 *
 * @author iulian.radulescu
 */
public class MicrodataMigrationProcessorOutput {

    private List<TMicrodata> microdata = new ArrayList<>();
    private List<ReportingUnitUser> reportingUnitUsers = new ArrayList<>();

    public MicrodataMigrationProcessorOutput(TMicrodata microdata, ReportingUnitUser reportingUnitUser) {
        this.microdata.add(microdata);
        if (reportingUnitUser != null) {
            this.reportingUnitUsers.add(reportingUnitUser);
        }
    }

    public List<TMicrodata> getMicrodata() {
        return this.microdata;
    }

    public void addMicrodata(TMicrodata microdata) {
        this.microdata.add(microdata);
    }

    public List<ReportingUnitUser> getReportingUnitUser() {
        return this.reportingUnitUsers;
    }

    public void addReportingUnitUser(ReportingUnitUser ruu) {
        this.reportingUnitUsers.add(ruu);
    }
}
