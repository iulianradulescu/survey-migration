/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.services.model;

/**
 *
 * @author iulian.radulescu
 */
public enum SurveyPopulation {
    ENTERPRISE(1), AUTO(2), ACCOMODATION(3), LIBRARIES(4), MUSEUMS(5), SHOWCOMPANIES(6), MEDICALUNITS(7), SCHOOLS(8);
    
    int id;
    SurveyPopulation( int id ) {
        this.id = id;
    }
    
    public int id( ) {
        return this.id;
    }
}
