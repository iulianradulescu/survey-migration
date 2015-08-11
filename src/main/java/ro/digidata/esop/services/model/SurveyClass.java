/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.services.model;

import java.util.Arrays;

/**
 *
 * @author iulian.radulescu
 */
public enum SurveyClass {

    SIMPLE("simpleMigrationJob", SurveyPopulation.ENTERPRISE, null),
    UNICA("unicaMigrationJob", SurveyPopulation.ENTERPRISE, "UNICA"),
    S3("s3MigrationJob", SurveyPopulation.ENTERPRISE, "S3"),
    TURISM("subSampleMigrationJob", SurveyPopulation.ACCOMODATION,"TURISM_1A","TURISM_1B"),
    CULT1("subSampleMigrationJob", SurveyPopulation.LIBRARIES,"CULT_1"),
    CULT2("subSampleMigrationJob", SurveyPopulation.MUSEUMS,"CULT_2"),
    CULT3("subSampleMigrationJob", SurveyPopulation.SHOWCOMPANIES,"CULT_3"),
    SAN("subSampleMigrationJob", SurveyPopulation.MEDICALUNITS, "SAN","SAN_2011","SAN_2012","SAN_2013","SAN_2014"),
    SC("subSampleMigrationJob", SurveyPopulation.SCHOOLS,"SC_2_2"),
    ASTRMP("subSampleMigrationJob", SurveyPopulation.AUTO,"ASTRM","ASTRP");

    private String jobName;

    private SurveyPopulation population;

    private String[] mnemonics;

    SurveyClass(String jobName, SurveyPopulation population, String... mnemonics) {
        this.jobName = jobName;
        this.population = population;
        this.mnemonics = mnemonics == null ? new String[0] : mnemonics;
    }

    public static SurveyClass classOf(String survey) {
        SurveyClass[] classes = SurveyClass.values();

        for (SurveyClass _class : classes) {
            if (Arrays.binarySearch(_class.mnemonics, survey) >= 0) {
                return _class;
            }
        }

        return SurveyClass.SIMPLE;
    }

    public String jobName() {
        return this.jobName;
    }
    
    public SurveyPopulation population( ) {
        return this.population;
    }
}
